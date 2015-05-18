package us.genetzky.alphaappdev.alphadroid;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import android.content.Context;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Created by Nathansen on 5/16/2015.
 */
import android.provider.ContactsContract.PhoneLookup;

public class DataContent{
    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

//    0: _id
//    1: thread_id
//    2: address
//    3: person
//    4: date
//    5: protocol
//    6: read
//    7: status
//    8: type
//    9: reply_path_present
//    10: subject
//    11: body
//    12: service_center
//    13: locked

    public static List<DataItem> getLastTextMessages(Context context, int numOfMessages){
        int numToDisplay;
        DataItem current;
        String nameOrPhone, dateString, timeString;
        long date;


        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c= context.getContentResolver().query(uri, null, null, null, null);
        if(numOfMessages==-1) {
            numToDisplay =c.getCount();
        }else{
            numToDisplay = numOfMessages;
        }

        List<DataItem> list= new ArrayList<>(numToDisplay);

        if(c.moveToFirst()){
            for(int i=0;i<numToDisplay;i++){
                current = new DataItem(DataItem.PhoneMessage_TAG);

                nameOrPhone = c.getString(c.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.ADDRESS));
                nameOrPhone = getContactName(context,nameOrPhone);

                date = c.getLong(c.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.DATE));
                timeString = new SimpleDateFormat("HH:mm").format(new Date(date));
                dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(date));

//                current.putString(DataItem.PhoneMessage_1NAME, nameOrPhone);
//                current.putString(DataItem.PhoneMessage_2TIME,timeString);
//                current.putString(DataItem.PhoneMessage_3DATE,dateString);
//                current.putString(DataItem.PhoneMessage_4MESSAGE, c.getString(c.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.BODY)));
                current = DataItem.newData_PhoneMessage(nameOrPhone,timeString,dateString,
                        c.getString(c.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.BODY)));
                list.add(current);

                Log.d("createPhoneMessages", current.toString());
                c.moveToNext();
            }
        }
        return list;
    }
    public static List<DataItem> getPairedDevices(Context context) throws Exception {
        BluetoothAdapter bluetoothAdapter = MyAlphaDroid.getBluetoothAdapter(context);
        if(bluetoothAdapter!=null){
            if(bluetoothAdapter.isEnabled()){
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if(pairedDevices!=null) {
                    List<DataItem> list = new ArrayList<>(pairedDevices.size());
                    DataItem current;
                    // If there are paired devices
                    if (pairedDevices.size() > 0) {
                        // Loop through paired devices
                        for (BluetoothDevice device : pairedDevices) {
                            // Add the name and address to show in a ListView
                            current = DataItem.newData_BluetoothDevice(device.getName(), device.getAddress(),
                                    "", "");
                            //String.valueOf(device.getBondState())
                            list.add(current);
                        }
                        return list;
                    } else {
                        throw new Exception("no_paired_devices");
                    }
                }else{
                    throw new Exception("null_paired_devices");
                }
            }else{
               throw new Exception("bluetooth_not_enabled");
            }
        }else{
            throw new Exception("device_has_no_bluetooth");
        }
    }
}

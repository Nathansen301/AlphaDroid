package us.genetzky.alphaappdev.alphadroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{
    public static final String SHAREDPREF = "SMS";
    public static final String KEY_PHONE = "sms_phone";
    public static final String KEY_MSG = "sms_msg";
    public static final String INTENT_ONRECEIVE = "";

    SharedPreferences mSharedPreferences;
    final SmsManager smsManager = SmsManager.getDefault();// Get the object of SmsManager
    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    String txtData = "SMS.in(" + senderNum + "):" + message;

                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,txtData, duration);
                    toast.show();
                    //Start RC activity
                    String data[] = {String.valueOf(2),senderNum,message};
                    AlphaDroidService.startActionRC(context, "SMS", data);
                } // end for loop

            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
//        Intent startSMS;
//        startSMS = new Intent();//(context,GCM.class);
//        startSMS.setClassName("us.genetzky.alphaappdev.alphadroid","us.genetzky.alphaappdev.alphadroid.SMS");
//        startSMS.putExtra(SMS.EXTRA_STATUS,"No new messages.");
//        context.startActivity(startSMS);
    }
}

package us.genetzky.alphaappdev.alphadroid;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

public class DataItem implements Parcelable{
    public static final String PhoneMessage_TAG = "PhoneMessage";
    public static final String PhoneMessage_1NAME = "name";
    public static final String PhoneMessage_2TIME = "time";
    public static final String PhoneMessage_3DATE = "date";
    public static final String PhoneMessage_4MESSAGE = "message";
    public static final String BluetoothDevice_TAG = "BluetoothDevice";
    public static final String BluetoothDevice_1NAME = "name";
    public static final String BluetoothDevice_2ADDRESS = "address";
    public static final String BluetoothDevice_3STATUS = "status";
    public static final String BluetoothDevice_4MESSAGE = "message";

    String[] mDataStrings= null;

    DataItem(String[] stringData){
        mDataStrings = stringData;
    }
    DataItem(String type){
        if(PhoneMessage_TAG.equals(type)) {
            mDataStrings = new String[5];
        }else if(BluetoothDevice_TAG.equals(type)){
            mDataStrings = new String[5];
        }else{
            mDataStrings =  new String[1];
        }
        mDataStrings[0] = type;
    }
    DataItem(Parcel in){
        in.readStringArray(mDataStrings);
    }
    static DataItem newData_BluetoothDevice(String name, String address, String status, String message){
        String[] dataString = {DataItem.BluetoothDevice_TAG,name,address,status,message};
        return new DataItem(dataString);
    }
    static DataItem newData_PhoneMessage(String name, String time, String date, String message){
        String[] dataString = {DataItem.BluetoothDevice_TAG,name,time,date,message};
        DataItem data = new DataItem(dataString);
        Log.d("DataItem:onCreate ", data.toString());
        return data;
    }

    String getString(int argNum){
        return mDataStrings[argNum];
    }
    void putString(String key, String value){
        mDataStrings[getArgNum(key)] = value;
    }
    @Override
    public String toString() {
        if(mDataStrings[0].equals(PhoneMessage_TAG)){
            return "@"+getString(0)+"("+getString(1)+"):"+getString(2);
        }else{
            return mDataStrings[0];
        }
    }
    int getArgNum(String datakey){
        int argNum = 0;
        if(PhoneMessage_TAG.equals(mDataStrings[0])) {
            switch(datakey){
                case PhoneMessage_TAG:
                    argNum= 0;break;
                case PhoneMessage_1NAME:
                    argNum= 1;break;
                case PhoneMessage_2TIME:
                    argNum= 2;break;
                case PhoneMessage_3DATE:
                    argNum= 3;break;
                case PhoneMessage_4MESSAGE:
                    argNum= 4;break;
                default:
                    argNum= 0;
            }
        }else if(BluetoothDevice_TAG.equals(mDataStrings[0])){
            switch(datakey){
                case BluetoothDevice_TAG:
                    argNum= 0;break;
                case BluetoothDevice_1NAME:
                    argNum= 1;break;
                case BluetoothDevice_2ADDRESS:
                    argNum= 2;break;
                case BluetoothDevice_3STATUS:
                    argNum= 3;break;
                case BluetoothDevice_4MESSAGE:
                    argNum= 4;break;
                default:
                    argNum= 0;
            }
        }
        return argNum;
    }
    String getDataKey(int argNum){
        String datakey;
        if(PhoneMessage_TAG.equals(mDataStrings[0])){
            switch(argNum){
                case 0:
                    datakey= PhoneMessage_TAG;break;
                case 1:
                    datakey= PhoneMessage_1NAME;break;
                case 2:
                    datakey= PhoneMessage_2TIME;break;
                case 3:
                    datakey= PhoneMessage_3DATE;break;
                case 4:
                    datakey= PhoneMessage_4MESSAGE;break;
                default:
                    datakey= null;
            }
        }else if(BluetoothDevice_TAG.equals(mDataStrings[0])){
            switch(argNum){
                case 0:
                    datakey= BluetoothDevice_TAG;break;
                case 1:
                    datakey= BluetoothDevice_1NAME;break;
                case 2:
                    datakey= BluetoothDevice_2ADDRESS;break;
                case 3:
                    datakey= BluetoothDevice_3STATUS;break;
                case 4:
                    datakey= BluetoothDevice_4MESSAGE;break;
                default:
                    datakey= null;
            }
        }else{
            datakey= mDataStrings[0];
        }
        Log.d("DataItem:getDataKey()","TAG:"+mDataStrings[0]+":("+argNum+")"+datakey);
        return datakey;
    }

    //*********************************************************************************************
    //          PARCELABLE
    //*********************************************************************************************
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeStringArray(mDataStrings);
    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}

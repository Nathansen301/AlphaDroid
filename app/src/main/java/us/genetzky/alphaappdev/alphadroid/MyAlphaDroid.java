package us.genetzky.alphaappdev.alphadroid;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nathansen on 5/17/2015.
 */
public class MyAlphaDroid extends Application {
    private Object myGloballyAccessibleObject;

    //Singleton pattern
    private static MyAlphaDroid singleInstance = null;
    public static MyAlphaDroid getInstance()
    {
        return singleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
    }
    public static void intent_startSettings(Context context){
        Intent startSettings;
        //startGCM = new Intent(this,GCM.class);
        startSettings = new Intent();
        startSettings.setClassName("us.genetzky.alphaappdev.alphadroid", "us.genetzky.alphaappdev.alphadroid.SettingsActivity");
        context.startActivity(startSettings);
    }

    public static BluetoothAdapter getBluetoothAdapter(Context context){
        if(ANDROID_atleastGINGERBREAD()){
            return((BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE)).getAdapter();
        }else{
            return BluetoothAdapter.getDefaultAdapter();
        }
    }

    public static boolean ANDROID_atleastGINGERBREAD(){
        return (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD);
    }


    public static int SETTINGS_SYNCSIZE(){
        return 100;
    }
}

package us.genetzky.alphaappdev.alphadroid;

import android.app.ListFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BT extends ActionBarActivity {
    public static final String EXTRA_STATUS = "us.genetzky.alphaappdev.alphadroid.bt.status";
    DataAdapter MyDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);

        Intent callingIntent = getIntent();
        String status = callingIntent.getStringExtra(EXTRA_STATUS);
        ((TextView) findViewById(R.id.bt_status)).setText(status);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.bt_enable){
            try{
                MyAlphaDroid.getBluetoothAdapter(this).enable();
                Toast.makeText(this, "Bluetooth is Enabled="+String.valueOf(MyAlphaDroid.getBluetoothAdapter(this).isEnabled()), Toast.LENGTH_LONG).show();
            } catch (NullPointerException e){
                Toast.makeText(this, "Error. Device has no Bluetooth", Toast.LENGTH_LONG).show();
            }

        }else if(id == R.id.bt_showpaired){
            showPairedFragment();
        }

        return super.onOptionsItemSelected(item);
    }
    void showPairedFragment(){
        try{
            List<DataItem> devices = DataContent.getPairedDevices(this);
            if(devices!=null){
                MyDevices = new DataAdapter(this, devices);
                ListFragment listFragment = new ListFragment();
                listFragment.setListAdapter(MyDevices);
                Log.d("showMessageFragment", devices.get(0).getString(2));
                getFragmentManager().beginTransaction()
                        .replace(R.id.bt_container, listFragment)
                        .commit();
            }
        }
        catch (Exception e){
            Log.e("showPairedFragment", e.getMessage());
            if(e.getMessage().equals("bluetooth_not_enabled")) {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
            }else if(e.getMessage().equals("no_paired_devices")) {
                Toast.makeText(this, "Error. No Paired Devices", Toast.LENGTH_LONG).show();
            }else if(e.getMessage().equals("device_has_no_bluetooth")) {
                Toast.makeText(this, "Error. Device has no Bluetooth", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "You dun fucked up", Toast.LENGTH_LONG).show();
            }
        }
    }
    void simpleMessage(String data){
        ((TextView)findViewById(R.id.bt_status)).setText(data);
    }
    class BTHandler extends Handler{
        public static final int SOCKET_CONNECTED = 0;
        public static final int DATA_RECEIVED = 1;
        public static final int MESSAGE_READ = 2;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SOCKET_CONNECTED:
                    //Update UI to indicate connection
                    break;
                case DATA_RECEIVED:
                    String data = (String) msg.obj;
                    simpleMessage(data);
                    break;
            }
        }
    }
}

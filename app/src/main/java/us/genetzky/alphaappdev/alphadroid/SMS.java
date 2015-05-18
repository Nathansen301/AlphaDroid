package us.genetzky.alphaappdev.alphadroid;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SMS extends ActionBarActivity implements Communicator{
    public static final String EXTRA_STATUS = "us.genetzky.alphaappdev.alphadroid.sms.status";

    DataAdapter MyMessages = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Intent callingIntent = getIntent();
        String status = callingIntent.getStringExtra(EXTRA_STATUS);
        ((TextView) findViewById(R.id.tv_status)).setText(status);
        if(status.equals(getString(R.string.sms_new))){
            showMessageFragment();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.SMS_send_text) {
            showSendFragment();
        }else if(id == R.id.SMS_check_text){
            showMessageFragment();
        }
        return super.onOptionsItemSelected(item);
    }
    void showSendFragment(){
        SMS_sendFragment sendFragment;
        sendFragment = SMS_sendFragment.newInstance("6055926260","Nathan Genetzky");
        getFragmentManager().beginTransaction()
                .replace(R.id.sms_container, sendFragment,"SEND_FRAG")
                .commit();
    }
    void showMessageFragment(){
        int numOfMessages = MyAlphaDroid.SETTINGS_SYNCSIZE();
        List<DataItem> messages = DataContent.getLastTextMessages(this,numOfMessages);
        if(messages.size()>=0){
            MyMessages = new DataAdapter(this, messages);
            ListFragment listFragment = new ListFragment();
            listFragment.setListAdapter(MyMessages);
            Log.d("showMessageFragment", messages.get(0).getString(2));

            getFragmentManager().beginTransaction()
                    .replace(R.id.sms_container, listFragment)
                    .commit();
        }else{
            Toast.makeText(this, "No SMS retrieved", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onFragmentEvent(String eventTag, View view) {

    }

    @Override
    public void onFragmentEvent(String response) {
        if(response.equals(SMS_sendFragment.ACTION_SEND)){
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
            Fragment frag = getFragmentManager().findFragmentByTag("SEND_FRAG");
            getFragmentManager().beginTransaction()
                    .remove(frag)
                    .commit();
        }
    }

    @Override
    public Bundle requestData(String tag, String[] keys) {
        return null;
    }
}

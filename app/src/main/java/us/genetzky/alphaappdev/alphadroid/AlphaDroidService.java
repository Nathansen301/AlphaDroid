package us.genetzky.alphaappdev.alphadroid;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlphaDroidService extends IntentService{
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_TX = "us.genetzky.alphaappdev.alphadroid.action.TX";
    private static final String ACTION_RC = "us.genetzky.alphaappdev.alphadroid.action.RC";

    private static final String EXTRA_1TYPE = "us.genetzky.alphaappdev.alphadroid.extra.type";
    private static final String EXTRA_2DATA = "us.genetzky.alphaappdev.alphadroid.extra.data";

    /**
     * Starts this service to perform action TX with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionTX(Context context, String type, String[] data) {
        Intent intent = new Intent(context, AlphaDroidService.class);
        intent.setAction(ACTION_TX);
        intent.putExtra(EXTRA_1TYPE, type);
        intent.putExtra(EXTRA_2DATA, data);
        context.startService(intent);
    }
    /**
     * Starts this service to perform action RC with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionRC(Context context, String type, String[] data) {
        Intent intent = new Intent(context, AlphaDroidService.class);
        intent.setAction(ACTION_RC);
        intent.putExtra(EXTRA_1TYPE, type);
        intent.putExtra(EXTRA_2DATA, data);
        context.startService(intent);
    }
    public AlphaDroidService() {
        super("AlphaDroidService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TX.equals(action)) {
                final String type = intent.getStringExtra(EXTRA_1TYPE);
                final String[] data = intent.getStringArrayExtra(EXTRA_2DATA);
                handleActionTX(type, data);
            } else if (ACTION_RC.equals(action)) {
                final String type = intent.getStringExtra(EXTRA_1TYPE);
                final String[] data = intent.getStringArrayExtra(EXTRA_2DATA);
                handleActionRC(type, data);
            }
        }
    }

    /**
     * Handle action TX in the provided background thread with the provided
     * parameters.
     */
    private void handleActionTX(String type, String[] data) {
        Log.d("handleActionTX","Type:"+type+" narg"+data[0]);
        SmsManager.getDefault().sendTextMessage(data[1], null, data[2], null, null);
    }

    /**
     * Handle action RC in the provided background thread with the provided
     * parameters.
     */
    private void handleActionRC(String type, String[] data) {
        Log.d("handleActionTX", "Type:" + type + " narg" + data[0]);

        Intent startSMS = new Intent(this,SMS.class);
        startSMS.putExtra(SMS.EXTRA_STATUS, getString(R.string.sms_new));
        startSMS.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startSMS);
    }
}

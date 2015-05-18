package us.genetzky.alphaappdev.alphadroid;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Nathansen on 5/16/2015.
 */
public interface Communicator {
    void onFragmentEvent(String eventTag, View view);
    void onFragmentEvent(String response);
    Bundle requestData(String tag, String[] keys);
}

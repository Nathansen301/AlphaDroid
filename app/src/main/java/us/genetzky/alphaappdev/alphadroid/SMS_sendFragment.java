package us.genetzky.alphaappdev.alphadroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SMS_sendFragment .OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SMS_sendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SMS_sendFragment extends Fragment {
    public static final String ACTION_SEND= "us.genetzky.alphaappdev.alphadroid.sms_sendfragment.send";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NUMBER1 = "number";
    private static final String ARG_NAME2 = "name";
    private static final String ARG_MESSAGE3 = "message";

    // TODO: Rename and change types of parameters
    private String mNumber;
    private String mName;
    private String mMessage;

    Button btnSend, btnPerson;
    EditText editText_phone, editText_message;

    private Communicator mComm;
    FragmentListener mListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param number Default Number
     * @param name Default Name.
     * @return A new instance of fragment SMS_sendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SMS_sendFragment newInstance(String number, String name) {
        SMS_sendFragment fragment = new SMS_sendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NUMBER1, number);
        args.putString(ARG_NAME2, name);
        args.putString(ARG_MESSAGE3, "");
        fragment.setArguments(args);
        return fragment;
    }

    public SMS_sendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNumber = getArguments().getString(ARG_NUMBER1);
            mName = getArguments().getString(ARG_NAME2);
            mMessage = getArguments().getString(ARG_MESSAGE3);
        }
        mListener = new FragmentListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_sms_send, container, false);
        btnSend = (Button) rootview.findViewById(R.id.sms_send);
        btnPerson = (Button) rootview.findViewById(R.id.sms_btn_person);
        editText_phone = (EditText) rootview.findViewById(R.id.sms_editphone);
        editText_message = (EditText) rootview.findViewById(R.id.sms_editmessage);

        btnPerson.setText("SendTo:"+mName);
        editText_phone.setText(mNumber);
        editText_message.setText(mMessage);

        btnSend.setOnClickListener(mListener);
        return rootview;
    }
    void sendTextMessage(){
        String data[] = {String.valueOf(2),getAddress(),getMessage()};
        AlphaDroidService.startActionTX(getActivity(),"SMS",data);
        //SmsManager.getDefault().sendTextMessage(getAddress(), null, getMessage(), null, null);
    }
    String getAddress(){
        return editText_phone.getText().toString();
    }
    String getMessage(){
        return editText_message.getText().toString();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mComm = (Communicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mComm = null;
    }
    class FragmentListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(btnSend.getId()==v.getId()){
                sendTextMessage();
                if (mComm != null) {
                    mComm.onFragmentEvent(ACTION_SEND);
                }
            }
        }
    }
}

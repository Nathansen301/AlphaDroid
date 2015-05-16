package us.genetzky.alphaappdev.alphadroid;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogonScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogonScreenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Name = "name";
    private static final String ARG_Phone = "phone";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mPhone;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Name of user
     * @param phone Phone of user
     * @return A new instance of fragment LogonScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogonScreenFragment newInstance(String name, String phone) {
        LogonScreenFragment fragment = new LogonScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Name, name);
        args.putString(ARG_Phone, phone);
        fragment.setArguments(args);
        return fragment;
    }

    public LogonScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_Name);
            mPhone = getArguments().getString(ARG_Phone);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logon_screen, container, false);
    }


}

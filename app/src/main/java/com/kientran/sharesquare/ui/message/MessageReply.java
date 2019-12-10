package com.kientran.sharesquare.ui.message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kientran.sharesquare.R;
import com.google.android.gms.plus.PlusOneButton;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.model.Message;

import org.w3c.dom.Text;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MessageReply#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageReply extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_content = "content";
    private static final String ARG_authorname = "authorname";
    private static final String ARG_authoremail = "authoremail";
    private static final String ARG_authorid = "authorid";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String content;
    private String authorname;
    private int authorid;
    private Button btn_Send;
    private EditText et_msg;
    private TextView tv_To;
    private final IMessageRepository repo;

//    private OnFragmentInteractionListener mListener;

    public MessageReply() {
        // Required empty public constructor
        repo = new MessageRepository(AppController.HOST);
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessageReply.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageReply newInstance(Message msg) {
        MessageReply fragment = new MessageReply();
        Bundle args = new Bundle();
        args.putString(ARG_content, msg.getContent());
        args.putString(ARG_authorname, msg.getAuthorname());
        args.putInt(ARG_authorid, msg.getAuthorid());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(ARG_content);
            authorname = getArguments().getString(ARG_authorname);
            authorid = getArguments().getInt(ARG_authorid);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_reply, container, false);

        btn_Send = view.findViewById(R.id.btn_Send);
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = et_msg.getText().toString().trim();
                if (msg.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter your message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                repo.ReplyMessage(authorid, msg);
                Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();

                MessageListFragment fmsg = new MessageListFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fmsg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        et_msg = view.findViewById(R.id.et_msg);
        et_msg.setText("\n=====From: "+authorname+" ======\n"+content);

        tv_To = view.findViewById(R.id.tv_To);
        tv_To.setText("To: " + authorname);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

}

package com.kientran.sharesquare.ui.send;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.kientran.sharesquare.R;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.ui.message.IMessageRepository;
import com.kientran.sharesquare.ui.message.MessageListFragment;
import com.kientran.sharesquare.ui.message.MessageRepository;

public class SendFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SendViewModel sendViewModel;
    private final IMessageRepository repo;
    ArrayAdapter<String> adapter;
    Spinner sp_To;
    EditText et_msg;

    public SendFragment() {
        repo = new MessageRepository(AppController.HOST);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        et_msg = root.findViewById(R.id.et_msg);

        sp_To = root.findViewById(R.id.sp_To);
        adapter = new ArrayAdapter<>(getActivity()
                , android.R.layout.simple_spinner_item
                , repo.getUsers());
        repo.SetAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_To.setAdapter(adapter);
        sp_To.setOnItemSelectedListener(this);

        final EditText et_searchto = root.findViewById(R.id.et_searchTo);
        et_searchto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //if the enter key was pressed, then hide the keyboard and do whatever needs doing.
                    String name = et_searchto.getText().toString().trim();
                    repo.searchUser(name);
                    return true;
                }
                return false;
            }
        });

//        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (filterLongEnough()) {
//                    Log.v("messages","lister");
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    sp_To.setAdapter(adapter);
//                }
//            }
//
//            private boolean filterLongEnough() {
//                return et_searchto.getText().toString().trim().length() > 2;
//            }
//        };
//        et_searchto.addTextChangedListener(fieldValidatorTextWatcher);

//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Button btn_send = root.findViewById(R.id.btn_Send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = sp_To.getSelectedItemPosition();
                if(position<0){
                    Toast.makeText(getActivity(), "Please select a recipient!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String msg = et_msg.getText().toString().trim();
                if(msg.length()==0){
                    Toast.makeText(getActivity(), "Please enter your message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                repo.SendMessage(position, msg);
                Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();

                MessageListFragment fmsg= new MessageListFragment();
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment,fmsg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
Log.v("message",position+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
package com.kientran.sharesquare.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kientran.sharesquare.R;
import com.kientran.sharesquare.ui.message.MesaageViewModel;
import com.kientran.sharesquare.ui.post.OnPostListFragmentInteractionListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPostListFragmentInteractionListener}
 * interface.
 */
public class DashboardFragment extends Fragment{

    private MesaageViewModel mesaageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mesaageViewModel =
                ViewModelProviders.of(this).get(MesaageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        mesaageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}

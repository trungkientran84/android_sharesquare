package com.kientran.sharesquare.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.kientran.sharesquare.R;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.model.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private UserProfile userProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            String name = getArguments().getString("Argument");
            System.out.print(name);
        }
        loadData(AppController.HOST + "/api/users/2");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView profileName = root.findViewById(R.id.tv_profileName);
        final TextView email = root.findViewById(R.id.tv_email);
        final NetworkImageView image = root.findViewById(R.id.img_avatar);
        final ImageLoader imageLoader = AppController.getInstance().getmImageLoader();

        profileViewModel.getProfileViewModelMutableLiveData().observe(this, new Observer<UserProfile>() {

            @Override
            public void onChanged(UserProfile userProfile) {
                profileName.setText(userProfile.getName());
                email.setText(userProfile.getEmail());
                image.setImageUrl(userProfile.getAvatar(),imageLoader);
            }
        });
        return root;
    }

    private void loadData(String url) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    userProfile = new UserProfile(data.getString("id"),data.getString("role_id"),
                            data.getString("name"),data.getString("email"),
                            AppController.HOST + data.getString("avatar"), data.getString("created_at"),
                            data.getString("updated_at"));
                    profileViewModel.setValue(userProfile);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("user", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("user", error.getMessage()

                );
            }
        });
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);

    }
}
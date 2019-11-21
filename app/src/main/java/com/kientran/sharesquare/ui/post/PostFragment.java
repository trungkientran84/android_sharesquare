package com.kientran.sharesquare.ui.post;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kientran.sharesquare.R;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPostListFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends Fragment {


    private OnPostListFragmentInteractionListener mListener;

    private static final List<Post> ITEMS = new ArrayList<Post>();

    private PostRecyclerViewAdapter adapter;

    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private String loadingDataUrl;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostFragment newInstance(int columnCount) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        //rgs.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            String name = getArguments().getString("Argument");
            System.out.print(name);
        }

        loadData(AppController.HOST + "/api/posts");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));


            adapter = new PostRecyclerViewAdapter(ITEMS, mListener);

            recyclerView.setAdapter(adapter);

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView
                                    .getLayoutManager();

                            totalItemCount = layoutManager.getItemCount();
                            lastVisibleItem = layoutManager
                                    .findLastVisibleItemPosition();

                            if (!loading &&
                                    totalItemCount <= (lastVisibleItem + visibleThreshold) && loadingDataUrl != "null") {

                                loadData(loadingDataUrl);

                                loading = true;
                            }
                        }
                    });
        }
        return view;
    }

    private void loadData(String url) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        Post post = new Post(jsonObject.getString("id"), jsonObject.getString("title"), AppController.HOST + jsonObject.getString("image"), jsonObject.getString("created_at"));
                        ITEMS.add(post);
                    }

                    loadingDataUrl = response.getJSONObject("links").getString("next");
                    System.out.println(loadingDataUrl);
                    adapter.notifyDataSetChanged();
                    loading = false;

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //TODO: If it to communicate with activity like send an event to activity then we need
        //to implement above commented code
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}

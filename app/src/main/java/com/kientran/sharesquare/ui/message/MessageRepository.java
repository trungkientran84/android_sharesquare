package com.kientran.sharesquare.ui.message;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MessageRepository implements IMessageRepository {
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private String loadingDataUrl;
    private final DateFormat df;
    private final String host;
    private LiveData<Message> messages;
    private RecyclerView.Adapter adapter;

    private final List<Message> ITEMS = new ArrayList<Message>();

    public MessageRepository(String host) {
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        loadingDataUrl = host + "/api/messagesload/53";
        this.host = host;
    }

    @Override
    public LiveData<Message> getMessages() {
        return null;
    }

    @Override
    public void GetMessageByPage(int totalItemCount, int lastVisibleItem) {
        if (!loading &&
                totalItemCount <= (lastVisibleItem + visibleThreshold)
                && loadingDataUrl != "null") {

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, loadingDataUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray data = response.getJSONArray("data");
                        Log.v("Message", "JSONArray");
                        try {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject = data.getJSONObject(i);
                                Message item = new Message(jsonObject.getInt("id")
                                        , jsonObject.getString("content")
                                        , jsonObject.getString("status")
                                        , df.parse(jsonObject.getString("created_at"))
                                        , jsonObject.getString("authorname")
                                        , jsonObject.getString("authoremail")
                                        , jsonObject.getInt("authorid")
                                );
                                ITEMS.add(item);
                            }
                        } catch (ParseException e) {
                            Log.e("Message", e.getMessage());

                        }
                        Log.v("Message", "for");

                        loadingDataUrl = response.getJSONObject("links").getString("next");
                        Log.v("Message", loadingDataUrl);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        loading = false;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Message", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Message", error.getMessage());
                }
            });

            AppController.getInstance().getRequestQueue().add(jsonArrayRequest);
            loading = true;
        }
    }

    @Override
    public List<Message> getITEMS() {
        return ITEMS;
    }

    @Override
    public void RemoveItem(final int position) {
        Message m = ITEMS.get(position);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.DELETE, host + "/api/messages/" + m.getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data = response.getString("data");

                    if (data.equals("success")) {
                        ITEMS.remove(position);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.e("Message", "Error in RemoveItem");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Message", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Message", error.getMessage());
            }
        });

        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);
    }

    @Override
    public void SetAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void SetRead(final int position) {
        Message m = ITEMS.get(position);
        m.setStatus("readed");

        JSONObject postparams = new JSONObject();
        try {
            postparams.put("id", m.getId());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Message", e.getMessage());
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST
                , host + "/api/messagesread"
                , postparams
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data = response.getString("data");

                    if (data.equals("success")) {
//                        ITEMS.remove(position);
//                        if (adapter != null) {
//                            adapter.notifyDataSetChanged();
//                        }
                    } else {
                        Log.e("Message", "Error in SetRead");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Message", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Message", error.getMessage());
            }
        });

        AppController.getInstance().

                getRequestQueue().

                add(jsonArrayRequest);
    }
}

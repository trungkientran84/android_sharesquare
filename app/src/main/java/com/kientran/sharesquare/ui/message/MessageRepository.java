package com.kientran.sharesquare.ui.message;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.model.Message;
import com.kientran.sharesquare.model.User;

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
    private boolean loading;
    private String loadingDataUrl;
    private final DateFormat df;
    private final String host;
    private LiveData<Message> messages;
    private RecyclerView.Adapter adapter;
    private ArrayAdapter adapterUser;

    private final List<Message> ITEMS = new ArrayList<Message>();
    private final List<User> _users = new ArrayList<>();
    private final List<String> _usersView = new ArrayList<>();

    public MessageRepository(String host) {
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        loadingDataUrl = host + "/api/messagesload/" + AppController.USER_ID;
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

                        loadingDataUrl = response.getJSONObject("links").getString("next");
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

    @Override
    public void searchUser(String username) {
        _users.clear();
        _usersView.clear();
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("name", username);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Message", e.getMessage());
        }
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST
                , host + "/api/messagesuser"
                , postparams
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        User item = new User(jsonObject.getString("id")
                                , jsonObject.getString("name")
                                , jsonObject.getString("email")
                        );

                        _users.add(item);
                        _usersView.add(item.getName() + "-" + item.getEmail());
                    }

                    Log.v("Message", "for");

                    if (adapterUser != null) {
                        adapterUser.notifyDataSetChanged();
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
    public List<String> getUsers() {
        return _usersView;
    }

    @Override
    public void SetAdapter(ArrayAdapter adapter) {
        this.adapterUser = adapter;
    }

    @Override
    public void SendMessage(int position, String content) {
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("created_user_id", AppController.USER_ID);
            postparams.put("content", content);
            postparams.put("recipient_id", _users.get(position).getId());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Message", e.getMessage());
        }
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST
                , host + "/api/messages"
                , postparams
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String data = response.getString("data");

                    if (data.equals("success")) {

                    } else {
                        Log.e("Message", "Error in SendMessage");

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
    public void ReplyMessage(int authorId, String content) {
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("created_user_id", AppController.USER_ID);
            postparams.put("content", content);
            postparams.put("recipient_id", authorId);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Message", e.getMessage());
        }
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST
                , host + "/api/messages"
                , postparams
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String data = response.getString("data");

                    if (data.equals("success")) {

                    } else {
                        Log.e("Message", "Error in ReplyMessage");

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


}

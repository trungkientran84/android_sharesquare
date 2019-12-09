package com.kientran.sharesquare.ui.message;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.kientran.sharesquare.model.Message;

import java.util.List;

public interface IMessageRepository {
    LiveData<Message> getMessages();
    void GetMessageByPage(int totalItemCount, int lastVisibleItem );
    List<Message> getITEMS();
    void RemoveItem(int position);
    void SetAdapter(RecyclerView.Adapter adapter);
    void SetRead(int position);
}

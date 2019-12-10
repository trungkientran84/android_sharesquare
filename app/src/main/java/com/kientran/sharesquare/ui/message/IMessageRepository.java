package com.kientran.sharesquare.ui.message;

import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.kientran.sharesquare.model.Message;

import java.util.List;

public interface IMessageRepository {
    LiveData<Message> getMessages();

    void GetMessageByPage(int totalItemCount, int lastVisibleItem);

    List<Message> getITEMS();

    void RemoveItem(int position);

    void SetAdapter(RecyclerView.Adapter adapter);

    void SetRead(int position);

    void searchUser(String username);

    List<String> getUsers();
    void SetAdapter(ArrayAdapter adapter);
void SendMessage(int position, String content);
    void ReplyMessage(int authorId, String content);

}

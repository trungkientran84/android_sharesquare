package com.kientran.sharesquare.ui.message;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kientran.sharesquare.model.Message;
import com.kientran.sharesquare.ui.message.MessageListFragment.OnListFragmentInteractionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.kientran.sharesquare.R;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMessageListRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageListRecyclerViewAdapter.ViewHolder> {

    private final List<Message> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final DateFormat df;
    private final IMessageRepository repo;
    private final FragmentManager manager;

    public MyMessageListRecyclerViewAdapter(OnListFragmentInteractionListener listener
            , IMessageRepository repo
            , FragmentManager manager
    ) {
        mValues = repo.getITEMS();// items;
        mListener = listener;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.repo = repo;
        this.manager = manager;
        repo.SetAdapter(this);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_messagelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("From: " + holder.mItem.getAuthorname());
        holder.mContentView.setText(holder.mItem.getContent());
        holder.mCreatedAt.setText(df.format(holder.mItem.getCreated_at()));
        UpdateReaded(holder);

        holder.mBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    repo.SetRead(position);
                    UpdateReaded(holder);
                }
            }
        });
        holder.mBtnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    repo.SetRead(position);
//                    UpdateReaded(holder);
                    MessageReply fmsg = MessageReply.newInstance(holder.mItem);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fmsg);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    repo.RemoveItem(position);
                    mListener.onMessageDelete(holder.mItem);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private void UpdateReaded(ViewHolder holder) {
        if (holder.mItem.getStatus().equals("readed")) {
            holder.mHeader.setBackgroundResource(R.color.MsgHeader_Readed);
            holder.mBtnRead.setBackgroundResource(R.color.MgsFooter_Gray);
            holder.mBtnRead.setEnabled(false);
        } else {
            holder.mHeader.setBackgroundResource(R.color.MsgHeader);
            holder.mBtnRead.setBackgroundResource(R.color.active);
            holder.mBtnRead.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mCreatedAt;
        public final Button mBtnDelete;
        public final Button mBtnRead;
        public final Button mBtnReply;
        public final ConstraintLayout mHeader;

        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tv_from);
            mContentView = (TextView) view.findViewById(R.id.tv_content);
            mCreatedAt = (TextView) view.findViewById(R.id.tv_createdAt);
            mBtnRead = (Button) view.findViewById(R.id.btn_Read);
            mBtnReply = (Button) view.findViewById(R.id.btn_Reply);
            mBtnDelete = (Button) view.findViewById(R.id.btn_Delete);
            mHeader = (ConstraintLayout) view.findViewById(R.id.CL_Header);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

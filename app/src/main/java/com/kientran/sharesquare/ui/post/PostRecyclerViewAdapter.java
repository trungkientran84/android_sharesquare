package com.kientran.sharesquare.ui.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kientran.sharesquare.R;
import com.kientran.sharesquare.app.AppController;
import com.kientran.sharesquare.model.Post;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Post} and makes a call to the
 * specified {@link OnPostListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private final List<Post> mValues;
    private final OnPostListFragmentInteractionListener mListener;

    public PostRecyclerViewAdapter(List<Post> items, OnPostListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).title);

        ImageLoader imageLoader = AppController.getInstance().getmImageLoader();
        holder.mThumbNail.setImageUrl(mValues.get(position).image, imageLoader);

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final NetworkImageView mThumbNail;
        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mThumbNail =  view.findViewById(R.id.thumbnail);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

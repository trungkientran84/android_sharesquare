package com.kientran.sharesquare.ui.post;

import com.kientran.sharesquare.model.Post;

public interface OnPostListFragmentInteractionListener {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    void onListFragmentInteraction(Post item);
}

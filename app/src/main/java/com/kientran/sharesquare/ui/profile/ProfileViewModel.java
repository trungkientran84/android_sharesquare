package com.kientran.sharesquare.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kientran.sharesquare.model.UserProfile;

public class ProfileViewModel extends ViewModel {


    public LiveData<UserProfile> getProfileViewModelMutableLiveData() {
        return profileViewModelMutableLiveData;
    }

    public void setValue(UserProfile userProfile) {
        profileViewModelMutableLiveData.setValue(userProfile);
    }

    private MutableLiveData<UserProfile> profileViewModelMutableLiveData;


    public ProfileViewModel() {
        profileViewModelMutableLiveData = new MutableLiveData<UserProfile>();
    }



}
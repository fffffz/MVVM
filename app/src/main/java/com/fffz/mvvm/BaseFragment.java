package com.fffz.mvvm;

import android.app.Fragment;

import com.kelin.mvvmlight.messenger.Messenger;

import com.fffz.mvvm.data.DataProvider;

public class BaseFragment extends Fragment {

    @Override
    public void onDestroyView() {
        DataProvider.get().cancel(this);
        Messenger.getDefault().unregister(this);
        super.onDestroyView();
    }
}

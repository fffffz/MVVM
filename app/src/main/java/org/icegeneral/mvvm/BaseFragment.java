package org.icegeneral.mvvm;

import android.app.Fragment;

import com.kelin.mvvmlight.messenger.Messenger;

import org.icegeneral.mvvm.data.DataProvider;

/**
 * Created by jianjun.lin on 2017/1/9.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onDestroyView() {
        DataProvider.get().cancel(this);
        Messenger.getDefault().unregister(this);
        super.onDestroyView();
    }
}

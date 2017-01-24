package org.icegeneral.mvvm;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kelin.mvvmlight.messenger.Messenger;
import com.viewpagerindicator.CirclePageIndicator;

import org.icegeneral.mvvm.databinding.FragmentMainBinding;
import org.icegeneral.mvvm.viewmodel.MainViewModel;

import rx.functions.Action0;

/**
 * Created by jianjun.lin on 2017/1/6.
 */

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @SuppressLint("ValidFragment")
    private MainFragment() {

    }

    private MainViewModel mMainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        final RecyclerView recyclerView = binding.recyclerView;
        Messenger.getDefault().register(this, MainViewModel.TOKEN_UPDATE_INDICATOR, new Action0() {
            @Override
            public void call() {
                recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (bindIndicator(recyclerView.getChildAt(0))) {
                            recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
        mMainViewModel = new MainViewModel(this);
        binding.setViewModel(mMainViewModel);
        return binding.getRoot();
    }

    private boolean bindIndicator(View headerView) {
        if (headerView == null) {
            return false;
        }
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) headerView.findViewById(R.id.viewpager);
        if (circlePageIndicator == null || viewPager == null) {
            return false;
        }
        circlePageIndicator.setViewPager(viewPager);
        return true;
    }

    @Override
    public void onDestroyView() {
        mMainViewModel.destroy();
        super.onDestroyView();
    }
}

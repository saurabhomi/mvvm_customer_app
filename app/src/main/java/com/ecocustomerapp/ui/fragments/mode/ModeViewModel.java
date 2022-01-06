package com.ecocustomerapp.ui.fragments.mode;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.databinding.FragmentModeBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;
import com.ecocustomerapp.utils.rx.SchedulerProvider;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ModeViewModel extends BaseViewModel<ModeNavigator, FragmentModeBinding> {
    public ModeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void chauffeurDriver() {
        getNavigator().replaceFragment(MainFragment.newInstance(new BookingRequest()), MainFragment.class.getName());
    }

    public void comingSoon() {
        Toast.makeText(getNavigator().getContext(), "Coming soon!", Toast.LENGTH_SHORT).show();    }

    void setPager() {
        List<Integer> sliderItems = new ArrayList<>();
        sliderItems.add(R.drawable.promo_image);
        sliderItems.add(R.drawable.promo_image);
        sliderItems.add(R.drawable.promo_image);
        ViewPager viewPager = getBinding().photosViewpager;
        viewPager.setAdapter(new ModePagerAdapter(getNavigator().getContext(), sliderItems));
        getBinding().viewpagerTab.setupWithViewPager(viewPager);
        View tab = ((ViewGroup) getBinding().viewpagerTab.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
        p.setMargins(50, 0, 50, 0);
    }


}

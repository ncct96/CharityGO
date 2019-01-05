package org.charitygo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> FragmentList = new ArrayList<>();
    private final List<String> FragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        FragmentList.add(fragment);
        FragmentTitleList.add(title);
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentList.get(i);
//        switch (i){
//            case 0: return DonationFragment.newInstance(0, "Donation Page");
//            case 1: return RedemptionFragment.newInstance(1, "Redemption Page");
//            default: return null;
//        }
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }
}

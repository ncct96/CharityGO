package org.charitygo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

import org.charitygo.R;
import org.charitygo.adapter.DonationFragment;
import org.charitygo.adapter.RedemptionFragment;
import org.charitygo.adapter.SectionPageAdapter;

public class FragmentPageActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    private SectionPageAdapter sectionPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        viewPager.setAdapter(new SectionPageAdapter(getSupportFragmentManager()));

//        // Give the PagerSlidingTabStrip the ViewPager
//        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        // Attach the view pager to the tab strip
//        tabsStrip.setViewPager(viewPager);

//        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            // This method will be invoked when a new page becomes selected.
//            @Override
//            public void onPageSelected(int position) {
////                Toast.makeText(FragmentPageActivity.this,
////                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
//            }
//
//            // This method will be invoked when the current page is scrolled
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                // Code goes here
//            }
//
//            // Called when the scroll state changes:
//            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // Code goes here
//            }
//        });
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DonationFragment(), "Donation History");
        adapter.addFragment(new RedemptionFragment(), "Redemption History");
        viewPager.setAdapter(adapter);
    }

//    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
//        final int PAGE_COUNT = 2;
//        private String tabTitles[] = new String[] { "Donation History", "Redemption History"};
//
//        public SampleFragmentPagerAdapter(FragmentManager fm) { super(fm); }
//
//        @Override
//        public int getCount() { return PAGE_COUNT; }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position){
//                case 0: return DonationFragment.newInstance(0, "Donation Page");
//                case 1: return RedemptionFragment.newInstance(1, "Redemption Page");
//                default: return null;
//            }
//            //return PageFragment.newInstance(position + 1);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Generate title based on item position
//            return tabTitles[position];
//        }
//    }

}

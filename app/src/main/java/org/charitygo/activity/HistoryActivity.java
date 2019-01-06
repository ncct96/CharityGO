package org.charitygo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;

import org.charitygo.R;
import org.charitygo.adapter.DonationFragment;
import org.charitygo.adapter.RedemptionFragment;
import org.charitygo.adapter.SectionPageAdapter;

public class HistoryActivity extends AppCompatActivity {

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
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DonationFragment(), "Donation History");
        adapter.addFragment(new RedemptionFragment(), "Redemption History");
        viewPager.setAdapter(adapter);
    }

}

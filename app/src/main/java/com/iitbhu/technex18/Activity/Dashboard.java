package com.iitbhu.technex18.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.iitbhu.technex18.R;
import com.iitbhu.technex18.fragment.HomeFragment;
import com.iitbhu.technex18.fragment.LeaderboardFragment;
import com.iitbhu.technex18.fragment.NotificationFragment;
import com.iitbhu.technex18.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.dashboard_combined);
        tabLayout.getTabAt(1).setIcon(R.drawable.leaderboard_combined);
        tabLayout.getTabAt(2).setIcon(R.drawable.notification_combined);
        tabLayout.getTabAt(3).setIcon(R.drawable.profile_combined);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        adapter.addFragment(new HomeFragment(), "ONE");
        adapter.addFragment(new LeaderboardFragment(), "TWO");
        adapter.addFragment(new NotificationFragment(), "THREE");
        adapter.addFragment(new ProfileFragment(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
        return null;
        }

    }
}
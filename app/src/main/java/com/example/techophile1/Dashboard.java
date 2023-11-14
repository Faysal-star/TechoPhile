package com.example.techophile1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class Dashboard extends Fragment {
    private TabLayout tLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        tLayout = view.findViewById(R.id.tLayout);
        viewPager = view.findViewById(R.id.viewPager);

        tLayout.setupWithViewPager(viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        pagerAdapter.addFragment(new Feed_tab(), "Feed");
        pagerAdapter.addFragment(new News_tab(), "News");
        pagerAdapter.addFragment(new Post_tab(), "Post");

        viewPager.setAdapter(pagerAdapter);


        return view;
    }
}
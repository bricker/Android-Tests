package com.bryanricker.hello_moon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class HelloMoonPagerActivity extends FragmentActivity
{

    private static final int NUM_PAGES = 2;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);

        setContentView(mViewPager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm)
        {
            @Override
            public int getCount()
            {
                return NUM_PAGES;
            }

            @Override
            public Fragment getItem(int position)
            {
                Fragment fragment = null;

                switch(position)
                {
                    case 0:
                        fragment = Fragment.instantiate(
                            HelloMoonPagerActivity.this, AudioFragment.class.getName());
                        break;
                    case 1:
                        fragment = Fragment.instantiate(
                            HelloMoonPagerActivity.this, VideoFragment.class.getName());
                        break;
                }

                return fragment;
            }
        });
    }

}

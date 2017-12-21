package com.example.kimaryeong.ariapp.fregment;


//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kim A Ryeong on 2017-11-20.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                MicFragment micFragment = new MicFragment();
                return micFragment;
            case 1:
                MapFragment mapFragment = new MapFragment();
                return mapFragment;
            case 2:
                MusicFragment musicFragment = new MusicFragment();
                return musicFragment;
            default:
                return null;
        }
    }

    public int getCount() {
        return tabCount;
    }
}

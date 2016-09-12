package me.thomasdkim.fitit.workout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by tdhyunk on 8/21/16.
 */

public class DateFragmentAdapter extends FragmentStatePagerAdapter {

    DateFragment[] fragList;
    int fragNum;

    public DateFragmentAdapter(FragmentManager fm, DateFragment[] fragList) {
        super(fm);
        this.fragNum = 3;
        this.fragList = fragList;
    }

    @Override
    public DateFragment getItem(int position) {

        return this.fragList[position];
    }

    @Override
    public int getCount() {

        return this.fragNum;
    }
}
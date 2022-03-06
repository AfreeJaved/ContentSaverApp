package com.example.instacontentsaver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new PhotoFragment();

            case 1:
                return new VideoFragment();

            case 2:
                return new ReelFragment();

            case 3:
                return new igtvFragment();

            case 4:
                return new ProfilePicFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}

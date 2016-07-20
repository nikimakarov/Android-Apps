package ru.surf.nikita_makarov.githubtrends.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.surf.nikita_makarov.githubtrends.fragment.PageFragment;

public class FragmentPageAdapter extends android.support.v4.app.FragmentPagerAdapter {

    public final int PAGE_COUNT = 2;
    private final static String RepositoriesString = "Repositories";
    private final static String UsersString = "Users";
    private String tabTitles[] = new String[] {RepositoriesString, UsersString};

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
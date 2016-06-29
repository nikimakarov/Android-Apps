package ru.surf.nikita_makarov.githubtrends.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import ru.surf.nikita_makarov.githubtrends.R;
import ru.surf.nikita_makarov.githubtrends.utils.DepthPageTransformer;
import ru.surf.nikita_makarov.githubtrends.utils.ParseTask;
import ru.surf.nikita_makarov.githubtrends.utils.FragmentPageAdapter;

public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public PagerAdapter pagerAdapter;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ParseTask().execute();
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}
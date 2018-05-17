package us.mifeng.stick_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import us.mifeng.stick_app.fragment.LiveFragment;
import us.mifeng.stick_app.fragment.MineFragment;
import us.mifeng.stick_app.fragment.PemFragment;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Fragment>fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        fragments.add(LiveFragment.newInstance("Live"));
        fragments.add(PemFragment.newInstance("Pem"));
        fragments.add(MineFragment.newInstance("Mine"));
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        LiveAdapter adapter = new LiveAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);


    }
    public class LiveAdapter extends FragmentPagerAdapter{

        public LiveAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

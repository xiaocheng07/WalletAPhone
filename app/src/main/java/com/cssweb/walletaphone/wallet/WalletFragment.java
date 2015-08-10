package com.cssweb.walletaphone.wallet;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.ui.AliPayGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhf on 2015/8/7.
 */
public class WalletFragment extends Fragment {
    private AliPayGridView gvMenu;

    ViewPager viewPager;
    List<View> pageViews = new ArrayList<View>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.wallet_fragment, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);



        // 九宫格菜单, 注意在Fragment中使用getView()和getActivity()
        gvMenu = (AliPayGridView) (getView().findViewById(R.id.gvMenu));
        gvMenu.setAdapter(new MyGridAdapter(getActivity()));


        // 广告栏
        LayoutInflater layoutInFlater = getLayoutInflater(savedInstanceState);
        pageViews.add(layoutInFlater.inflate(R.layout.wallet_ads_viewpager_1, null));
        pageViews.add(layoutInFlater.inflate(R.layout.wallet_ads_viewpager_2, null));
        pageViews.add(layoutInFlater.inflate(R.layout.wallet_ads_viewpager_3, null));

        viewPager = (ViewPager) getView().findViewById(R.id.vpAds);
        viewPager.setAdapter(new AdsPagerAdapter());
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new AdsOnPageChangeListener());
    }

    public class AdsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object)
        {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2)
        {
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1)
        {
            ((ViewPager)arg0).addView(pageViews.get(arg1));

            return pageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1)
        {

        }

        @Override
        public Parcelable saveState()
        {
            return null;
        }

        @Override
        public void startUpdate(View arg0)
        {

        }
        @Override
        public void finishUpdate(View arg0)
        {

        }

    }
    public class AdsOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}

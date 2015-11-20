package com.cssweb.walletaphone.wallet;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.ui.gridview.AliPayGridView;
import com.cssweb.walletaphone.ui.gridview.BaseViewHolder;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_fragment, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);



        initGridView();


        initViewPager(savedInstanceState);

    }

    private void initGridView()
    {
        // 九宫格菜单, 注意在Fragment中使用getView()和getActivity()
        gvMenu = (AliPayGridView) (getView().findViewById(R.id.gvMenu));
        gvMenu.setAdapter(new GridViewAdapter(getActivity()));
        gvMenu.setOnItemClickListener(new ItemClickListener());
    }

    class GridViewAdapter extends BaseAdapter {
        private Context context;

        public String[] img_text = {"webview", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票", "当面付", "亲密付", "机票",};

        public int[] imgs = {R.drawable.app_transfer, R.drawable.app_fund,
                R.drawable.app_phonecharge, R.drawable.app_creditcard,
                R.drawable.app_movie, R.drawable.app_lottery,
                R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane};

        public GridViewAdapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {

            return img_text.length;
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent, false);
            }

            TextView tv = BaseViewHolder.get(convertView, R.id.tvGridItem);
            ImageView iv = BaseViewHolder.get(convertView, R.id.ivGridItem);

            iv.setBackgroundResource(imgs[position]);
            tv.setText(img_text[position]);

            return convertView;
        }
    }

    class ItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // adapterView //The AdapterView where the click happened
            //view The view within the AdapterView that was clicked
            //i The position of the view in the adapter
            //l he row id of the item that was clicked

        }
    }

    private void initViewPager(Bundle savedInstanceState)
    {
        LayoutInflater layoutInFlater = getLayoutInflater(savedInstanceState);
        pageViews.add(layoutInFlater.inflate(R.layout.wallet_ads_viewpager_1, null));
        pageViews.add(layoutInFlater.inflate(R.layout.wallet_ads_viewpager_2, null));
        pageViews.add(layoutInFlater.inflate(R.layout.wallet_ads_viewpager_3, null));

        viewPager = (ViewPager) getView().findViewById(R.id.vpAds);
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new PageChangeListener());
    }

    public class ViewPagerAdapter extends PagerAdapter {

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

    public class PageChangeListener implements ViewPager.OnPageChangeListener {

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

package com.cssweb.walletaphone.wallet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.login.view.LoginActivity;
import com.cssweb.walletaphone.nfc.test.NFCChangShaActivity;
import com.cssweb.walletaphone.ui.gridview.AliPayGridView;
import com.cssweb.walletaphone.ui.gridview.BaseViewHolder;
import com.cssweb.walletaphone.webview.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhf on 2015/8/7.
 */
public class WalletFragment extends Fragment {


    private static final String LOG_TAG = "WalletFragment";

    private AliPayGridView gvMenu;
    List<GridViewItem> gvItems = new ArrayList<GridViewItem>();

    ViewPager viewPager;
    ArrayList<View> pageViews = new ArrayList<View>();

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
        gvItems.add(new GridViewItem(0, "NFC",R.drawable.app_transfer));
        gvItems.add(new GridViewItem(1, "MVP",R.drawable.app_fund));
        gvItems.add(new GridViewItem(2, "webview",R.drawable.app_phonecharge));
        gvItems.add(new GridViewItem(3, "mvvm",R.drawable.app_creditcard));
        gvItems.add(new GridViewItem(4, "淘宝电影",R.drawable.app_movie));
        gvItems.add(new GridViewItem(5, "彩票",R.drawable.app_lottery));
        gvItems.add(new GridViewItem(6, "当面付",R.drawable.app_facepay));
        gvItems.add(new GridViewItem(7, "亲密付",R.drawable.app_close));
        gvItems.add(new GridViewItem(8, "机票",R.drawable.app_plane));

        // 九宫格菜单, 注意在Fragment中使用getView()和getActivity()
        gvMenu = (AliPayGridView) (getView().findViewById(R.id.gvMenu));
        gvMenu.setAdapter(new GridViewAdapter(getActivity()));
        gvMenu.setOnItemClickListener(new ItemClickListener());


    }

    class GridViewItem
    {
        int id;
        String text;
        int imageResId;

        public GridViewItem(int id, String text, int imageResId)
        {
            this.id = id;
            this.text = text;
            this.imageResId = imageResId;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public int getImageResId() {
            return imageResId;
        }
    }

    class GridViewAdapter extends BaseAdapter {
        private Context context;



        public GridViewAdapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {

            return gvItems.size();
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

            GridViewItem item = gvItems.get(position);
            iv.setBackgroundResource(item.getImageResId());
            tv.setText(item.getText());

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
            Log.d(LOG_TAG, "i=" + i);
            Log.d(LOG_TAG, "l=" + l);

            GridViewItem gvItem = gvItems.get(i);
            Log.d(LOG_TAG, "item id=" + gvItem.getId());

            switch (gvItem.getId())
            {
                case 0:
                    Intent intentNFC = new Intent(getActivity(), NFCChangShaActivity.class);
                    startActivity(intentNFC);
                    break;
                case 1:
                    Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLogin);
                    break;
                case 2:
                    Intent intentWebview = new Intent(getActivity(), WebviewActivity.class);
                    startActivity(intentWebview);
                    break;
                default:
                    break;
            }


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

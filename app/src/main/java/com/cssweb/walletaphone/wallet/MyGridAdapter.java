package com.cssweb.walletaphone.wallet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cssweb.walletaphone.R;
import com.cssweb.walletaphone.ui.BaseViewHolder;


public class MyGridAdapter extends BaseAdapter {
	private Context context;

	public String[] img_text = { "转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",			"当面付", "亲密付", "机票", };

	public int[] imgs = { R.drawable.app_transfer, R.drawable.app_fund,
			R.drawable.app_phonecharge, R.drawable.app_creditcard,
			R.drawable.app_movie, R.drawable.app_lottery,
			R.drawable.app_facepay, R.drawable.app_close, R.drawable.app_plane };

	public MyGridAdapter(Context context) {
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

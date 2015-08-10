package com.cssweb.walletaphone.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by chenhf on 2015/8/10.
 */

public class AliPayGridView extends GridView {

    public AliPayGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AliPayGridView(Context context) {
        super(context);
    }

    public AliPayGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}

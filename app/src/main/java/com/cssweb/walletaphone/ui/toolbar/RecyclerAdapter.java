package com.cssweb.walletaphone.ui.toolbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cssweb.walletaphone.R;

/**
 * Created by chenh on 2015/11/29.
 */
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private String[] texts;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);

        texts = new String[20];

        for (int i=0; i<20; i++)
            texts[i] = "item" + i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);

        ViewHolder  viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvItem.setText(texts[position]);
    }

    @Override
    public int getItemCount() {
        return texts.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvItem = (TextView) itemView.findViewById(R.id.tvRecyclerViewItem);
        }
    }
}

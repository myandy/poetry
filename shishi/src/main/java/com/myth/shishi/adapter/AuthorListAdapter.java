package com.myth.shishi.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.poetrycommon.db.ColorDatabaseHelper;
import com.myth.poetrycommon.entity.ColorEntity;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.VerticalTextView;
import com.myth.shishi.R;
import com.myth.shishi.activity.AuthorPageActivity;
import com.myth.shishi.adapter.AuthorListAdapter.ViewHolder.ViewHolderItem;
import com.myth.shishi.entity.Author;
import com.myth.shishi.wiget.CircleStringView;

import java.util.List;

public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.ViewHolder> {
    private Context mContext;

    private List<Author> list;

    public void setList(List<Author> list) {
        this.list = list;
    }

    private List<ColorEntity> mColorEntities;

    public AuthorListAdapter(Context context) {
        mContext = context;
        mColorEntities = ColorDatabaseHelper.getAllShow();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderItem holder1;

        ViewHolderItem holder2;

        public class ViewHolderItem {
            Author author;

            View item;

            RelativeLayout head;

            ViewGroup middle;

            TextView num;

            TextView name;

            VerticalTextView enname;

            CircleStringView stoneView;
        }

        public ViewHolder(View convertView, ViewGroup parent) {
            super(convertView);
            View view = convertView.findViewById(R.id.content);
            view.setLayoutParams(new LinearLayout.LayoutParams(-2, parent.getMeasuredHeight()));
            holder1 = new ViewHolderItem();
            holder2 = new ViewHolderItem();
            holder1.item = convertView.findViewById(R.id.item1);
            holder1.head = (RelativeLayout) convertView.findViewById(R.id.head1);
            holder1.middle = (ViewGroup) convertView.findViewById(R.id.middle1);
            holder1.num = (TextView) convertView.findViewById(R.id.num_1);
            holder1.name = (TextView) convertView.findViewById(R.id.name1);
            holder1.enname = (VerticalTextView) convertView.findViewById(R.id.enname1);
            holder1.stoneView = new CircleStringView(parent.getContext());
            android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
                    ResizeUtils.getInstance().dip2px(30), ResizeUtils.getInstance().dip2px(30));
            holder1.middle.addView(holder1.stoneView, layoutParams);

            holder2.item = convertView.findViewById(R.id.item2);
            holder2.head = (RelativeLayout) convertView.findViewById(R.id.head2);
            holder2.middle = (ViewGroup) convertView.findViewById(R.id.middle2);
            holder2.num = (TextView) convertView.findViewById(R.id.num_2);
            holder2.name = (TextView) convertView.findViewById(R.id.name2);
            holder2.enname = (VerticalTextView) convertView.findViewById(R.id.enname2);
            holder2.stoneView = new CircleStringView(parent.getContext());
            holder2.middle.addView(holder2.stoneView, layoutParams);
        }
    }

    @Override
    public AuthorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cipai_item, parent, false);
        ViewHolder holder = new ViewHolder(convertView, parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        initHolderView(holder.holder1, 2 * position);
        initHolderView(holder.holder2, 2 * position + 1);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size() / 2);
    }

    private Author getItem(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }


    private void initHolderView(final ViewHolderItem holder, int pos) {
        holder.author = getItem(pos);
        if (holder.author != null) {
            final int color = mColorEntities.get(pos % mColorEntities.size()).toColor();
            holder.item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AuthorPageActivity.class);
                    holder.author.color = color;
                    intent.putExtra("author", holder.author);
                    mContext.startActivity(intent);
                }
            });

            holder.head.setBackgroundColor(color);
            holder.num.setTextColor(color);
            holder.name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.enname.setTextColor(mContext.getResources().getColor(R.color.white));

            holder.stoneView.setType(holder.author.dynasty, color);
            holder.num.setText(String.format("%03d", holder.author.p_num));
            holder.name.setText(holder.author.author + "");
            holder.enname.setText(holder.author.en_name);
        }
    }


}

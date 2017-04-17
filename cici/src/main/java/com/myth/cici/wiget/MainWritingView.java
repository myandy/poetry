package com.myth.cici.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.StackView;
import android.widget.TextView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.entity.Writing;
import com.myth.poetrycommon.utils.ResizeUtil;

import java.util.List;

public class MainWritingView extends RelativeLayout {

    private Context mContext;

    private List<Writing> mWritings;

    public MainWritingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainWritingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainWritingView(Context context, List<Writing> writings) {
        super(context);
        mContext = context;
        mWritings = writings;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_intro, null);

        final TextView title = (TextView) root.findViewById(R.id.title);
        title.setTypeface(MyApplication.instance.getTypeface());
        final StackView stackView = (StackView) root.findViewById(R.id.stack_view);
        ResizeUtil.getInstance().layoutSquareView(stackView);

        MainWritingAdapter colorAdapter = new MainWritingAdapter();
        stackView.setAdapter(colorAdapter);
        stackView.getLayoutParams().height = ResizeUtil.resize(mContext, 600);
        stackView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title.setText(mWritings.get(position).getCipai().getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addView(root, new LayoutParams(-1, -1));
    }

    public class MainWritingAdapter extends BaseAdapter {


        public int getCount() {
            return mWritings == null ? 0 : mWritings.size();
        }

        public Object getItem(int position) {
            return mWritings == null ? null : mWritings.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = new LinearLayout(mContext);
                holder.mShareView = new ShareView(mContext, mWritings.get(position));

                ((ViewGroup) convertView).addView(holder.mShareView);
                ResizeUtil.getInstance().layoutSquareView(holder.mShareView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mShareView.setWriting(mWritings.get(position));
            return convertView;
        }

        public class ViewHolder {
            ShareView mShareView;
        }

    }

}

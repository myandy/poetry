//package com.myth.poetrycommon.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.myth.poetrycommon.activity.EditActivity;
//import com.myth.poetrycommon.activity.SearchListActivity;
//import com.myth.poetrycommon.adapter.BaseAdapter;
//import com.myth.poetrycommon.utils.ResizeUtils;
//import com.myth.poetrycommon.view.TouchEffectImageView;
//import com.myth.shishi.MyApplication;
//import com.myth.shishi.R;
//import com.myth.poetrycommon.db.FormerDatabaseHelper;
//import com.myth.poetrycommon.entity.Former;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class FormerSearchActivity extends SearchListActivity<Former> {
//
//    private boolean isEdit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        isEdit = getIntent().getBooleanExtra("edit", false);
//        if (isEdit) {
//            setBottomVisible();
//            addBottomView();
//        }
//    }
//
//    private void addBottomView() {
//        getBottomLeftView().setImageResource(R.drawable.add);
//        getBottomLeftView().setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mActivity, FormerIntroActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        ImageView setting = new TouchEffectImageView(mActivity, null);
//        setting.setImageResource(R.drawable.dict);
//        setting.setScaleType(ImageView.ScaleType.FIT_XY);
//        setting.setPadding(18, 18, 18, 18);
//        addBottomRightView(setting,
//                new ViewGroup.LayoutParams(ResizeUtils.getInstance().dip2px(48), ResizeUtils.getInstance().dip2px(48)));
//    }
//
//    @Override
//    public List<Former> getData() {
//        return  FormerDatabaseHelper.getAll();
//    }
//
//    @Override
//    public List<Former> searchList(String word) {
//        ArrayList<Former> list = new ArrayList<>();
//        for (Former data : originList) {
//            if (dataname.contains(word)) {
//                list.add(data);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public int getSearchHint() {
//        return R.string.search_former_hint;
//    }
//
//    @Override
//    public BaseAdapter.OnItemClickListener getItemClickListener() {
//        return new BaseAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(int position) {
//                if (isEdit) {
//                    Intent intent = new Intent(mActivity, FormerActivity.class);
//                    intent.putExtra("former", list.get(position));
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(mActivity, EditActivity.class);
//                    intent.putExtra("former", list.get(position));
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };
//    }
//
//
//    @Override
//    public BaseAdapter getSearchListAdapter() {
//        return new FormerSearchAdapter();
//    }
//
//
//    public static class FormerSearchAdapter extends BaseAdapter<Former> {
//
//        public static class ViewHolder extends BaseHolder {
//            public TextView name;
//            public TextView tag;
//
//            public ViewHolder(View arg0) {
//                super(arg0);
//                name = (TextView) arg0.findViewById(com.myth.poetrycommon.R.id.name);
//                tag = (TextView) arg0.findViewById(com.myth.poetrycommon.R.id.tag);
//            }
//
//        }
//
//        @Override
//        public void onBindViewHolder(BaseHolder holder, int position) {
//            super.onBindViewHolder(holder, position);
//            ViewHolder viewHolder = (ViewHolder) holder;
//            viewHolder.name.setText(list.get(position)name);
//
//            viewHolder.name.setTypeface(MyApplication.instance.getTypeface());
//            viewHolder.tag.setTypeface(MyApplication.instance.getTypeface());
//        }
//
//        @Override
//        protected int getLayoutId() {
//            return com.myth.poetrycommon.R.layout.adapter_cipai;
//        }
//
//        @Override
//        protected BaseHolder getHolder(View view) {
//            return new ViewHolder(view);
//        }
//
//    }
//}

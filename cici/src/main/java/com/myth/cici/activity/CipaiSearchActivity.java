//package com.myth.cici.activity;
//
//import android.content.Intent;
//import android.view.View;
//import android.widget.TextView;
//
//import com.myth.cici.MyApplication;
//import com.myth.cici.R;
//import com.myth.cici.db.CipaiDatabaseHelper;
//import com.myth.cici.entity.Cipai;
//import com.myth.cici.wiget.StoneView;
//import com.myth.poetrycommon.activity.EditActivity;
//import com.myth.poetrycommon.activity.SearchListActivity;
//import com.myth.poetrycommon.adapter.BaseAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class CipaiSearchActivity extends SearchListActivity<Cipai> {
//    @Override
//    public List<Cipai> getData() {
//        return CipaiDatabaseHelper.getAllCipai();
//    }
//
//    @Override
//    public List<Cipai> searchList(String word) {
//        ArrayList<Cipai> list = new ArrayList<>();
//        for (Cipai cipai : originList) {
//            if (cipai.name.contains(word)) {
//                list.add(cipai);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public int getSearchHint() {
//        return R.string.search_cipai_hint;
//    }
//
//    @Override
//    public BaseAdapter.OnItemClickListener getItemClickListener() {
//        return new BaseAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(mActivity, EditActivity.class);
//                intent.putExtra("former", list.get(position));
//                startActivity(intent);
//                finish();
//            }
//        };
//    }
//
//    @Override
//    protected void initView() {
//        super.initView();
//    }
//
//    @Override
//    public BaseAdapter getSearchListAdapter() {
//        return new CipaiSearchAdapter();
//    }
//
//
//    public static class CipaiSearchAdapter extends BaseAdapter<Cipai> {
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
//            viewHolder.name.setText(list.get(position).name);
//            viewHolder.tag.setText(StoneView.getYunString(list.get(position).tone_type) + " ● "
//                    + list.get(position).wordcount);
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

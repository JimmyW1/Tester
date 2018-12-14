package com.test.ui.activities.other.test_tools.study;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.study.self_views.MyListView;
import com.test.ui.activities.other.test_tools.study.self_views.MySelfViewPager;

import java.util.ArrayList;
import java.util.HashMap;

public class TestScrollCollision1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll_collision1);
        MySelfViewPager mySelfViewPager = (MySelfViewPager) findViewById(R.id.myselfviewpager_study);

        MyListView listView = (MyListView) findViewById(R.id.listview_study_collision1);
        listView.setMySelfViewPager(mySelfViewPager);
        MyListView listView2 = (MyListView) findViewById(R.id.listview_study_collision2);
        listView2.setMySelfViewPager(mySelfViewPager);
        MyListView listView3 = (MyListView) findViewById(R.id.listview_study_collision3);
        listView3.setMySelfViewPager(mySelfViewPager);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("2222222222222222222222");
        arrayList.add("2222222222222222222222");
        arrayList.add("2222222222222222222222");
        arrayList.add("2222222222222222222222");
        arrayList.add("2222222222222222222222");
        arrayList.add("2222222222222222222222");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        arrayList.add("1111111111111");
        MyListAdapter adapter = new MyListAdapter(arrayList);
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter);
        listView3.setAdapter(adapter);
    }

    private class MyListAdapter implements ListAdapter {
        ArrayList<String> itemNameList;

        public MyListAdapter(ArrayList<String> itemNameList) {
            this.itemNameList = itemNameList;
        }

        //点击相关设置
        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        //这两个方法是系统希望在Adapter中数据有变化时得到通知。通知做啥？刷新进图条的长度^_^。
        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return itemNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemNameList.get(position);
        }

        /**
         * getItemId是干嘛用的？在调用 invalidateView()时，ListView会刷新显示内容。
         * 如果内容的id是有效的，系统会跟据id来确定当前显示哪条内容，也就是firstVisibleChild的位置。id是否有效通过hasStableIds()确定。
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            TextView textView;

            if (convertView != null) {
                textView = (TextView) convertView.getTag();
                view = convertView;
            } else {
                view = getLayoutInflater().inflate(R.layout.listview_item, null);
                textView = (TextView) view.findViewById(R.id.item_content);
                view.setTag(textView);
            }

            textView.setText(itemNameList.get(position));
            textView.setTextColor(Color.BLACK);

            return view;
        }

        /**
         * getItemViewType和getViewTypeCount作用如下:
         * 如果ListView需要显示多种类型的内容，就需要有不同的缓存拿来使用。
         * 举个例子，position是奇数时getView()返回的是A类型的View；偶数的时候返回B类型的View。
         * 那么就需要在getViewTypeCount()中返回2；在getItemViewType()中当position是单数时返回一个值，双数时返回另外一个值。
         * ListView根据返回值缓存和重用View，这样在getView()中调用强制类型转换就不会出现类型转换错误了。
         */
        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        /* 至少返回1 */
        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            if (itemNameList.size() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}

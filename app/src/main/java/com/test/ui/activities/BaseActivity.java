package com.test.ui.activities;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.test.ui.activities.other.setting.SettingsActivity;
import com.test.util.DensityUtil;
import com.test.util.LogUtil;
import com.test.util.SystemUtil;

import java.util.ArrayList;

/**
 * Created by CuncheW1 on 2017/4/17.
 */

public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";
    PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View viewRoot = layoutInflater.inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) viewRoot.findViewById(R.id.layout_content);
        Button btn_back = (Button) viewRoot.findViewById(R.id.action_bar_back);
        btn_back.setOnClickListener(getOnClickListener());
        Button btn_overflow = (Button) viewRoot.findViewById(R.id.action_bar_overflow);
        btn_overflow.setOnClickListener(getOnClickListener());
        if (MyApplication.getInstance().getMainActivity() == this) {
            btn_back.setVisibility(View.GONE);
            btn_overflow.setVisibility(View.GONE);
        }

        View contentView = layoutInflater.inflate(layoutResID, null);
        frameLayout.addView(contentView);
        super.setContentView(viewRoot);
    }

    @Override
    public void setContentView(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View viewRoot = layoutInflater.inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.layout_content);
        Button btn_back = (Button) viewRoot.findViewById(R.id.action_bar_back);
        btn_back.setOnClickListener(getOnClickListener());
        Button btn_overflow = (Button) viewRoot.findViewById(R.id.action_bar_overflow);
        btn_overflow.setOnClickListener(getOnClickListener());
        if (MyApplication.getInstance().getMainActivity() == this) {
            btn_back.setVisibility(View.GONE);
            btn_overflow.setVisibility(View.GONE);
        }

        frameLayout.addView(view);
        super.setContentView(viewRoot);
    }

    @Override
    protected void onDestroy() {
        MyApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    private View.OnClickListener getOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.action_bar_back:
                        onBackPressed();
                        break;
                    case R.id.action_bar_overflow:
                        popUpOverflowWindow(v);
                        break;
                }
            }
        };
        return listener;
    }

    private void popUpOverflowWindow(View view) {
        if (popupWindow == null) {
            final View popupView = getLayoutInflater().inflate(R.layout.overflow_listview, null);
            final ListView listView = (ListView) popupView.findViewById(R.id.overflow_listview);
            final ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(SystemUtil.getResString(R.string.overflow_settings));
            arrayList.add(SystemUtil.getResString(R.string.overflow_update));
            arrayList.add(SystemUtil.getResString(R.string.overflow_about));
            listView.setAdapter(new MyListAdapter(arrayList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LogUtil.d(TAG, "position=" + position);
                    if (position == 0) {
                        Intent intent = new Intent(MyApplication.getContext(), SettingsActivity.class);
                        startActivity(intent);
                    }
                    popupWindow.dismiss();
                }
            });

            int px = DensityUtil.dip2px(MyApplication.getContext(), 100);
            popupWindow = new PopupWindow(popupView, px, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_backgroundcolor, null));
            popupWindow.setOutsideTouchable(false);
            popupWindow.setTouchable(true);
            popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        }
        popupWindow.showAsDropDown(view,  Gravity.RIGHT | Gravity.TOP, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        return super.onTouchEvent(event);
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

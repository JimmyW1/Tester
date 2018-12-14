package com.test.ui.activities.manual_test.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.logic.service_tester.base_datas.CaseEntry;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/3/2.
 */

public class CaseListExpandableAdapter extends BaseExpandableListAdapter {
    ArrayList<String> groupList;
    Map<String, ArrayList<CaseEntry>> dataMap;
    Context context;

    public CaseListExpandableAdapter(Map<String, ArrayList<CaseEntry>> dataMap) {
        context = MyApplication.getContext();
        setDataMap(dataMap);
    }

    public void setDataMap(Map<String, ArrayList<CaseEntry>> dataMap) {
        this.dataMap = dataMap;
        Iterator iterator = dataMap.keySet().iterator();
        groupList = new ArrayList<String>();

        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            groupList.add(key);
        }
    }

    @Override
    public int getGroupCount() {
        Log.i("Cunche", "====getFroupCount["+dataMap.size()+"]");
        return dataMap.size();
    }

    @Override
    public int getChildrenCount(int parentPos) {
        Log.i("Cunche", "====getChildrenCount["+parentPos + " " + dataMap.get(groupList.get(parentPos)).size()+"]");
        return dataMap.get(groupList.get(parentPos)).size();
    }

    @Override
    public Object getGroup(int parentPos) {
        Log.i("Cunche", "==getGroup i="+ parentPos);

        return groupList.get(parentPos);
    }

    @Override
    public Object getChild(int parentPos, int childPos) {
        Log.i("Cunche", "==getChild i="+ parentPos + "i1=" + childPos);
        Log.i("Cunche", "==return String "+ dataMap.get(groupList.get(parentPos)).get(childPos).getCaseId());
        return dataMap.get(groupList.get(parentPos)).get(childPos).getCaseId();
    }

    @Override
    public long getGroupId(int parentPos) {
        Log.i("Cunche", "==getGroupId i="+ parentPos);
        return parentPos;
    }

    @Override
    public long getChildId(int parentPos, int childPos) {
        Log.i("Cunche", "==getChildId i="+ childPos);
        return childPos;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView caseGroupNameView;
        ImageView indicatorImage;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.expand_layout, null);
            caseGroupNameView = (TextView) view.findViewById(R.id.case_name_text);
            indicatorImage = (ImageView) view.findViewById(R.id.indicator_image);

            viewHolder = new ViewHolder();
            viewHolder.imageView = indicatorImage;
            viewHolder.textView = caseGroupNameView;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            caseGroupNameView = viewHolder.textView;
            indicatorImage = viewHolder.imageView;
        }

        caseGroupNameView.setText(groupList.get(i));
        Log.i("Cunche", "Index=" + i + "B_expand=" + b);
        Log.i("Cunche", groupList.get(i));

        if (b) {
            indicatorImage.setImageDrawable(context.getResources().getDrawable(R.drawable.expandable_normal, null));
        } else {
            indicatorImage.setImageDrawable(context.getResources().getDrawable(R.drawable.expandable_selector, null));
        }

        return view;
    }

    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        TextView caseNameView;
        ImageView autoTestFlagImage;
        ViewHolder viewHolder;

        Log.i("Cunche", "===getChildView++++");
        if (view == null) {
            Log.i("Cunche", "===getChildView+ null +++");
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.expand_item, null);

            caseNameView = (TextView) view.findViewById(R.id.casename_text);
            autoTestFlagImage = (ImageView) view.findViewById(R.id.t_autotest_image);

            viewHolder = new ViewHolder();
            viewHolder.textView = caseNameView;
            viewHolder.imageView = autoTestFlagImage;

            view.setTag(viewHolder);
        }
        else {
            Log.i("Cunche", "===getChildView+ not null +++");

            viewHolder = (ViewHolder) view.getTag();
            caseNameView = viewHolder.textView;
            autoTestFlagImage = viewHolder.imageView;
        }

        caseNameView.setText(dataMap.get(groupList.get(parentPos)).get(childPos).getCaseId());
        if (!dataMap.get(groupList.get(parentPos)).get(childPos).isSupportAutoTest()) {
            autoTestFlagImage.setVisibility(View.VISIBLE);
        }

        view.setBackgroundColor(Color.WHITE);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView  textView;
    }
}

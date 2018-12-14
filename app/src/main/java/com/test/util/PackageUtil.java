package com.test.util;

import com.test.ui.activities.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by CuncheW1 on 2017/8/2.
 */

public class PackageUtil {
    private static final String TAG = "PackageUtil";

    public static List<String> getClassName(String packageName){
        List<String> classNameList=new ArrayList<String >();

        if (packageName == null || packageName.length() == 0) {
            return classNameList;
        }

        try {
            DexFile df = new DexFile(MyApplication.getInstance().getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式

            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();

                if (className.startsWith(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    if (!className.matches(".*\\$.*")) { // 去除子类等
                        LogUtil.d(TAG, "className=" + className);
                        int idx = className.substring(packageName.length() + 1).indexOf("."); // 去除包中包含的子包的类
                        LogUtil.d(TAG, "idx=" + idx);

                        if (idx == -1) {
                            classNameList.add(className);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  classNameList;
    }
}

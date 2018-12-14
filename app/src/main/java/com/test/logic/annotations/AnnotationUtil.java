package com.test.logic.annotations;

import com.test.util.LogUtil;

import java.lang.reflect.Method;

/**
 * Created by CuncheW1 on 2017/8/8.
 */

public class AnnotationUtil {
    private static final String TAG = "AnnotationUtil";
    public static int       DEFAULT_VISABLELEVEL = 10;
    public static boolean   DEFAULT_SUPPORTAUTOTEST = false;

    public static int getVisableLevel(Method caseMethod) {
        if (isCaseAttributesAnnotation(caseMethod)) {
            CaseAttributes caseAttributes = caseMethod.getAnnotation(CaseAttributes.class);
            int visableLevel = caseAttributes.visableLevel();
            LogUtil.d(TAG, "visableLevel=" + visableLevel);
            return visableLevel;
        }

        return DEFAULT_VISABLELEVEL;
    }

    public static boolean isSupportAutoTest(Method caseMethod) {
        if (isCaseAttributesAnnotation(caseMethod)) {
            CaseAttributes caseAttributes = caseMethod.getAnnotation(CaseAttributes.class);
            boolean isSupportAutoTest = caseAttributes.isSupportAutoTest();
            LogUtil.d(TAG, "isSupportAutoTest=" + isSupportAutoTest);
            return isSupportAutoTest;
        }

        return DEFAULT_SUPPORTAUTOTEST;
    }

    public static String getCaseDescription(Method caseMethod) {
        if (isCaseAttributesAnnotation(caseMethod)) {
            CaseAttributes caseAttributes = caseMethod.getAnnotation(CaseAttributes.class);
            return caseAttributes.description();
        }

        return "";
    }

    public static boolean isCaseAttributesAnnotation(Method caseMethod) {
        if (caseMethod != null) {
            CaseAttributes caseAttributes = caseMethod.getAnnotation(CaseAttributes.class);
            if (caseAttributes != null) {
                return true;
            }
        }

        return false;
    }

    public static String getTestDetailCaseAnnotationDescription(Method caseMethod) {
        if (caseMethod != null) {
            TestDetailCaseAnnotation annotation = caseMethod.getAnnotation(TestDetailCaseAnnotation.class);
            if (annotation != null) {
                return "Case: " + annotation.itemDetailName() + "\n预期结果：" + annotation.resDialogTitle() + "\n";
            }
        }

        return "";
    }

    public static int getDescriptionRid(Method caseMethod) {
        if (isCaseAttributesAnnotation(caseMethod)) {
            CaseAttributes caseAttributes = caseMethod.getAnnotation(CaseAttributes.class);
            int descriptRid = caseAttributes.descriptRid();
            LogUtil.d(TAG, "descriptRid=" + descriptRid);
            return descriptRid;
        }

        return -1;
    }
}

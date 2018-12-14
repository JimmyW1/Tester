package com.test.logic.testmodules.basemodule;

import com.test.ui.activities.MyApplication;
import com.test.util.TVLog;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by CuncheW1 on 2017/8/1.
 */

public class BaseModule {
    MyApplication myApplication;
    private static final String TAG="BaseModule";
    /**
     * testResult、lock 和 resultCondition作用：
     * 当测试案例的结果需要Service使用listener等方式异步告知的时候，
     */
    Lock lock;
    boolean testResult;
    Condition resultCondition;

    public BaseModule() {
        lock = new ReentrantLock();
        resultCondition = lock.newCondition();
    }

    /**
     * 获取执行过程和结果展示的窗口，永远不会返回null，并调用其中的showInfoInNewLine或者showInfo显示想要显示的信息。
     * @return 显示窗口操作对象
     */
    public TVLog getResultTv() {
        if (myApplication == null) {
            myApplication = MyApplication.getInstance();
        }
        return myApplication.getCurrentTestEntry().getResultTv();
    }

    /**
     * 获取展示测试用例说明和预期的窗口，永远不会返回null，并调用其中的showInfoInNewLine或者showInfo显示想要显示的信息。
     * 如果在注解CaseAttribute注解中填写的description,调用此函数会在展示窗口中接着description的内容显示
     * @return 显示窗口操作对象
     */
    public TVLog getDescriptionTv() {
        if (myApplication == null) {
            myApplication = MyApplication.getInstance();
        }
        return myApplication.getCurrentTestEntry().getDescriptionTv();
    }

    /**
     * 获取 模块句柄，用于模块内访问别的模块的函数
     * @param moduleName 别的模块对应的类名, 比如想访问ModuleBeeper中public的函数，就调用getModuleInstance("ModuleBeeper");
     * @return 模块实例, 如果没有对应实例返回null
     */
    public BaseModule getModuleInstance(String moduleName) {
        if (myApplication == null) {
            myApplication = MyApplication.getInstance();
        }

        if (myApplication.getCurrentTestEntry().getModuleEntryByModuleName(moduleName) != null) {
            return myApplication.getCurrentTestEntry().getModuleEntryByModuleName(moduleName).getModule();
        }

        return null;
    }

    /**
     * 当测试case获取结果为异步通知的时候, 用于测试用例最后等待异步通知测试结果，配合notifyResult函数使用
     * @return 测试结果
     */
    public boolean waitResult() {
        boolean loopRun = true;

        lock.lock();
        while (loopRun) {
            try {
                resultCondition.await();
                loopRun = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();

        return testResult;
    }

    /**
     * 当测试case结果为异步通知的时候, 一般用于放在listener回调函数中通知测试结果，配合waitResult使用
     * @param isSuccess
     */
    public void notifyResult(boolean isSuccess) {
        lock.lock();
        testResult = isSuccess;
        resultCondition.signalAll();
        lock.unlock();
    }
}

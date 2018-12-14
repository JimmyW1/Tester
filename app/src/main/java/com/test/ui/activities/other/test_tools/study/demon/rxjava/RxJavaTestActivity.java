package com.test.ui.activities.other.test_tools.study.demon.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.test.ui.activities.R;
import com.test.util.LogUtil;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxJavaTestActivity extends AppCompatActivity {
    private final String TAG = "RxJavaTestActivity";
    @BindView(R.id.button12) Button button12;
    @BindView(R.id.button14) Button button14;
    @BindView(R.id.button15) Button button15;
    @BindView(R.id.button16) Button button16;
    @BindView(R.id.button17) Button button17;
    @BindView(R.id.button18) Button button18;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button12)
    void doButton12() {
        Observable<Integer> observableString = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i= 0; i < 5; i++) {
                    e.onNext(i);
                }

                e.onComplete();
            }
        });

        observableString.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.d(TAG, "integer=" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "===========onComplete");
            }
        });
    }

    @OnClick(R.id.button14)
    void doButton14() {
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);

        Observable<Integer> observableList = Observable.fromIterable(list);
        observableList.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.d(TAG, "integer=" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "===========onComplete");
            }
        });
    }

    private String helloWorld() {
        return "helloworld";
    }

    @OnClick(R.id.button15)
    void doButton15() {
        Observable<String> observableString = Observable.just(helloWorld());
        observableString.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.d(TAG, "String=" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "===========onComplete");

            }
        });
    }

    @OnClick(R.id.button16)
    void doButton16() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.subscribeActual(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.d(TAG, "String=" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "=========complete");

            }
        });

        publishSubject.onNext("nihao");

    }

    @OnClick(R.id.button17)
    void doButton17() {
        final PublishSubject<Boolean> publishSubject = PublishSubject.create();
        publishSubject.subscribeActual(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                LogUtil.d(TAG, "Boolean=" + aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for(int i = 0; i < 5; i++) {
                    e.onNext(i);
                }
                e.onComplete();
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                publishSubject.onNext(true);
            }
        }).subscribe();
    }
    @OnClick(R.id.button18)
    void doButton18() {
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.onNext("111");

        behaviorSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.d(TAG, "BehaviorSubject1 str=" + s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "========1===finish");
            }
        });

        behaviorSubject.onNext("22222");
        behaviorSubject.onNext("33333");
        behaviorSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.d(TAG, "BehaviorSubject2 str=" + s);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(TAG, "======2=====finish");
            }

            @Override
            public void onComplete() {

            }
        });
        behaviorSubject.onComplete();


    }

}

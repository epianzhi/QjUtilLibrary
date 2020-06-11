package com.qj.ulibrary.retrofit.http;


import android.support.annotation.NonNull;


import com.qj.ulibrary.retrofit.model.HttpResult;
import com.qj.ulibrary.retrofit.util.ActivityLifeCycleEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 */

public class RxHelper {

    private static int code;

    /**
     * 利用Observable.takeUntil()停止网络请求
     *
     * @param event
     * @param lifecycleSubject
     * @param <T>
     * @return
     */
    @NonNull
    public <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceObservable) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return sourceObservable.takeUntil(compareLifecycleObservable);
            }
        };
    }


    /**
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> tObservable) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return tObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> result) {


                        //在这里对返回结果进行预处理，此项目为：当code为200时返回model数据，否则抛出异常。

                        Observable<T> data = null;
                        Observable<T> error = null;
                        try {
                            code = result.getCode();
                            if (result.getCode() == 200) {
//                                Hawk.put("msg", result.getMessage());
                                data = createData(result.getData());
                                return data;
                            } else {
                                ////根据code判断错误类型，本项目暂时不用，直接返回message
                                error = Observable.error(new ApiException(result.getCode(), result.getMessage()));
                                return error;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return data;


                    }
                }).takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     *
     *
     */


    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }

}

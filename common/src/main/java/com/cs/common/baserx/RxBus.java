package com.cs.common.baserx;

import android.support.annotation.NonNull;

import com.cs.common.utils.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by zhuhui on 2016/10/14.
 * RxJava实现的EventBus
 */

public class RxBus {
    private static volatile RxBus instance;

    private RxBus(){}

    public static  RxBus getInstance() {
        if (null == instance) {
            synchronized (RxBus.class){
                if(null == instance){
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }
    private ConcurrentHashMap<Object,List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    public <T>Observable<T> register(@NonNull Object tag){
        List<Subject> subjectList = subjectMapper.get(tag);
        if(null == subjectList){
            subjectList = new ArrayList<>();
            subjectMapper.put(tag,subjectList);
        }
        Subject<T,T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    public void unregister(@NonNull Object tag){
        List<Subject> subjects = subjectMapper.get(tag);
        if(null != subjects){
            subjectMapper.remove(tag);
        }
    }

    /**
     * 取消监听
     *
     * @param tag
     * @param observable
     * @return
     */
    @SuppressWarnings("rawtypes")
    public RxBus unregister(@NonNull Object tag,
                            @NonNull Observable<?> observable) {
        if (null == observable)
            return getInstance();
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjects.remove((Subject<?, ?>) observable);
            if (isEmpty(subjects)) {
                subjectMapper.remove(tag);
                Logger.d("unregister"+ tag + "  size:" + subjects.size());
            }
        }
        return getInstance();
    }

    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    /**
     * 触发事件
     *
     * @param content
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void post(@NonNull Object tag, @NonNull Object content) {
        Logger.d("post"+ "eventName: " + tag);
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
                Logger.d("onEvent"+ "eventName: " + tag);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }
}

package com.gm.ultifi.base.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPool helper
 */
public class TaskRunner {

    private static final String TAG = TaskRunner.class.getSimpleName();

    private static final int CORE_THREADS = 3;
    private static final long KEEP_ALIVE_SECONDS = 60L;

    private static TaskRunner sTaskRunner = null;

    private final ThreadPoolExecutor mExecutor;

    private final Handler mHandler;

    private TaskRunner() {
        mHandler = new Handler(Looper.getMainLooper());
        mExecutor = newThreadPoolExecutor();
    }

    public static TaskRunner getInstance() {
        if (sTaskRunner == null) {
            sTaskRunner = new TaskRunner();
        }
        return sTaskRunner;
    }

    private ThreadPoolExecutor newThreadPoolExecutor() {
        return new ThreadPoolExecutor(CORE_THREADS,
                Integer.MAX_VALUE,
                KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    public void execute(Runnable runnable) {
        mExecutor.execute(runnable);
    }

    public <R> void executeCallable(Callable<R> callable, OnCompletedCallback<R> callback) {
        mExecutor.execute(() -> {
            try {
                R result = callable.call();
                mHandler.post(() -> callback.onComplete(result));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                mHandler.post(() -> callback.onComplete((R) ("finalResult: " + e.getMessage())));
            }
        });
    }

    public ExecutorService getExecutor() {
        return mExecutor;
    }

    public void shutdownService() {
        ThreadPoolExecutor threadPoolExecutor = mExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }
    }

    public interface OnCompletedCallback<R extends Object> {
        void onComplete(R value);
    }
}
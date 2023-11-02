package com.hzjt.platform.account.user.infrastructure.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AccountThreadPoolManager
 * 功能描述：全系统通用线程池管理
 *
 * @author zhanghaojie
 * @date 2023/11/1 15:23
 */
public class AccountThreadPoolManager {
    //线程池
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());

    //获取线程池
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    //提交任务
    public static void submit(Runnable runnable) {
        threadPoolExecutor.submit(runnable);
    }
}


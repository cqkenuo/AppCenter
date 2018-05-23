package cn.lt.android.main.threadpool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.lt.android.db.AppEntity;

/**
 * @author chengyong
 * @time 2016/10/10 22:16
 * @des 线程池代理
 */
public class ThreadPoolProxy {
    ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private ArrayList<String> cacheInstallList = new ArrayList<>();
    private Map<String,Runnable> taskMap = new HashMap<>();
    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    /**
     * 创建ThreadPoolExecutor
     * 双重检查加锁,保证只有在第一次初始化的时候才启用同步机制,提高了性能
     */
    private void initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mExecutor = new ThreadPoolExecutor(
                            mCorePoolSize, //核心池的大小
                            mMaximumPoolSize,//最大线程数
                            keepAliveTime,//保持时间
                            unit,//保持时间的单位
                            workQueue, //任务队列
                            threadFactory,//线程工厂
                            handler//异常捕获器
                    );
                }
            }

        }
    }


    /**
     * 1.提交任务
     *  暂时只有轮询安装用到
     * @param task
     * @param entity
     */
    public void submit(Runnable task, AppEntity entity) {
        if(!existTask(entity.getPackageName())){
            initThreadPoolExecutor();
            cacheInstallList.add(entity.getPackageName());
            mExecutor.submit(task);
            taskMap.put(entity.getPackageName(),task);
        }
    }


    /**
     * 2.执行任务
     *
     * @param task
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    private boolean existTask(String pkgName){
        for (String packageName : cacheInstallList) {
            if(packageName.equals(pkgName)){
                return true;
            }
        }
        return false;
    }
    /**
     * 3.移除任务
     *
     * @param task
     */
    public void remove(Runnable task,String packageName) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
        cacheInstallList.remove(packageName);
    }
    /**
     * 3.移除任务
     *
     * @param task
     */
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }

    public void remove(String packageName) {
        initThreadPoolExecutor();
        mExecutor.remove(taskMap.remove(packageName));
        cacheInstallList.remove(packageName);
    }


}

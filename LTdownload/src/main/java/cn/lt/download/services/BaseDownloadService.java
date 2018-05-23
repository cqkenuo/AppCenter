package cn.lt.download.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import cn.lt.download.util.FileDownloadLog;


public abstract class BaseDownloadService<CALLBACK extends IInterface, BINDER extends Binder> extends Service {

    private final RemoteCallbackList<CALLBACK> callbackList = new RemoteCallbackList<>();
    private BINDER binder;

    protected BINDER getBinder() {
        return this.binder;
    }

    protected void register(CALLBACK callback) {
        FileDownloadLog.d(this, "register onEvent: %s", callback);
        if (callback != null) {
            callbackList.register(callback);
        }
    }

    protected void unregister(CALLBACK callback) {
        FileDownloadLog.d(this, "un register onEvent: %s", callback);
        if (callback != null) {
            callbackList.unregister(callback);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloadLog.d(this, "onCreate");
        binder = createBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileDownloadLog.d(this, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileDownloadLog.d(this, "onStartCommand");
        // TODO change command when no task in thread
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        FileDownloadLog.d(this, "onStart");
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        FileDownloadLog.d(this, "onBind %s", intent);
        return getBinder();
    }

    protected abstract BINDER createBinder();

    protected synchronized int callback(final int cmd, Object... objects) {
        final int n = callbackList.beginBroadcast();
        try {
            for (int i = 0; i < n; i++) {
                if (handleCallback(cmd, callbackList.getBroadcastItem(i), objects))
                    break;
            }

        } catch (RemoteException e) {
            FileDownloadLog.e(this, e, "onEvent error");
        } finally {
            callbackList.finishBroadcast();
        }

        return n;
    }

    /**
     * @param cmd     handle by cmd
     * @param objects params
     * @return is consume
     */
    protected abstract boolean handleCallback(final int cmd, final CALLBACK callback, Object... objects) throws RemoteException;

}

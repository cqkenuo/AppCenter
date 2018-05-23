package cn.lt.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.lt.download.event.ServiceStatusEvent;
import cn.lt.download.util.FileDownloadLog;

public abstract class BaseServiceAgent<CALLBACK extends Binder, INTERFACE extends IInterface> implements ServiceConnection {

    private final CALLBACK callback;
    private INTERFACE service;
    private final Class<?> serviceClass;

    private final HashMap<String, Object> uiCacheMap = new HashMap<>();

    protected CALLBACK getCallback() {
        return this.callback;
    }

    protected INTERFACE getService() {
        return this.service;
    }

    protected BaseServiceAgent(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
        this.callback = createCallback();
    }

    protected abstract CALLBACK createCallback();

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = asInterface(service);

        FileDownloadLog.d(this, "onServiceConnected %s %s", name, this.service);

        try {
            registerCallback(this.service, this.callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        final List<Runnable> runnableList = (List<Runnable>) connectedRunnableList.clone();
        connectedRunnableList.clear();
        for (Runnable runnable : runnableList) {
            runnable.run();
        }

        DownloadEventBusManager.getEventBus().
                publishByService(new ServiceStatusEvent(ServiceStatusEvent.ConnectStatus.connected, serviceClass));

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        FileDownloadLog.d(this, "onServiceDisconnected %s %s", name, this.service);
        releaseConnect();
    }

    private void releaseConnect() {
        if (this.service != null) {
            try {
                unregisterCallback(this.service, this.callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        FileDownloadLog.d(this, "release connect resources %s", this.service);
        this.service = null;

        DownloadEventBusManager.getEventBus().
                publishByService(new ServiceStatusEvent(ServiceStatusEvent.ConnectStatus.disconnected, serviceClass));
    }

    private final List<Context> BIND_CONTEXTS = new ArrayList<>();
    private final ArrayList<Runnable> connectedRunnableList = new ArrayList<>();

    protected void bindStartByContext(final Context context) {
        bindStartByContext(context, null);
    }

    protected void bindStartByContext(final Context context, final Runnable connectedRunnable) {
        FileDownloadLog.d(this, "bindStartByContext %s", context.getClass().getSimpleName());

        Intent i = new Intent(context, serviceClass);
        if (connectedRunnable != null) {
            if (!connectedRunnableList.contains(connectedRunnable)) {
                connectedRunnableList.add(connectedRunnable);
            }
        }

        if (!BIND_CONTEXTS.contains(context)) {
            // 对称,只有一次remove，防止内存泄漏
            BIND_CONTEXTS.add(context);
        }

        context.bindService(i, this, Context.BIND_AUTO_CREATE);
        context.startService(i);
    }

    protected void unbindByContext(final Context context) {
        if (!BIND_CONTEXTS.contains(context)) {
            return;
        }

        FileDownloadLog.d(this, "unbindByContext %s", context);

        BIND_CONTEXTS.remove(context);


        if (BIND_CONTEXTS.isEmpty()) {
            releaseConnect();
        }

        Intent i = new Intent(context, serviceClass);
        context.unbindService(this);
        context.stopService(i);
    }


    public void startService(final Context context) {

        Intent i = new Intent(context, serviceClass);
        context.startService(i);
    }

    protected abstract INTERFACE asInterface(IBinder service);

    protected abstract void registerCallback(final INTERFACE service, final CALLBACK callback) throws RemoteException;

    protected abstract void unregisterCallback(final INTERFACE service, final CALLBACK callback) throws RemoteException;


    protected Object popCache(final String key) {
        return uiCacheMap.remove(key);
    }

    protected String putCache(final Object object) {
        if (object == null) {
            return null;
        }
        final String key = object.toString();
        uiCacheMap.put(key, object);
        return key;
    }

    public boolean isConnected() {
        return getService() != null;
    }

    public void testServiceException() {
        this.service = null;
    }
}

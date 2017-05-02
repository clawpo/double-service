package cn.ucai.prometheusservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class LocalService extends Service {
    MyBinder binder;
    MyConn conn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        conn = new MyConn();
    }

    class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public String getServiceName() throws RemoteException {
            return LocalService.class.getSimpleName();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(LocalService.this, " 本地服务started", Toast.LENGTH_SHORT).show();
        this.bindService(new Intent(LocalService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(LocalService.this, "远程服务killed", Toast.LENGTH_SHORT).show();
            //开启远程服务
            LocalService.this.startService(new Intent(LocalService.this, RomoteService.class));
            //绑定远程服务
            LocalService.this.bindService(new Intent(LocalService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //开启远程服务
        LocalService.this.startService(new Intent(LocalService.this, RomoteService.class));
        //绑定远程服务
        LocalService.this.bindService(new Intent(LocalService.this, RomoteService.class), conn, Context.BIND_IMPORTANT);

    }
}

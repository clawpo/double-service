package cn.ucai.prometheusservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(MainActivity.this, "程序启动", Toast.LENGTH_SHORT).show();
        createPrometheus();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(MainActivity.this, "程序被杀", Toast.LENGTH_SHORT).show();
        createPrometheus();
        super.onDestroy();
    }

    private void createPrometheus(){
        Toast.makeText(MainActivity.this, "造神", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(MainActivity.this,LocalService.class));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(MainActivity.this,RomoteService.class));
            }
        }).start();
    }
}

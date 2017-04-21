package com.example.aasthu.verbroseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread mythread=new Thread()
        {
            @Override
            public void run()
            {
                try{
                    sleep(2000);
                    Intent in=new Intent(getApplicationContext(),getdata.class);
                    startActivity(in);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}

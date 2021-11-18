package com.example.countingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Thread thread1;
    Thread thread2;
    RadioGroup rg;
    int selected=100;
    int count=0;
    EditText TEXT;
    boolean THrunning =false;
    boolean COrunning =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        TEXT=(EditText) findViewById(R.id.editText);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i==R.id.radioButton){
                    selected=100;
                }
                else if(i==R.id.radioButton2) {
                    selected=300;
                }
                else if(i==R.id.radioButton3) {
                    selected=500;
                }
                else{
                    selected=1000;
                }
            }

        });
    }

    public void onClickServiceOnClicked(View view) {
        String content=TEXT.getText().toString();
        int time = selected;

        if(!THrunning){
            thread1 = new Thread(()->{
                try {
                    THrunning=true;
                    Log.d(TAG, "Start Service");

                    if(!content.isEmpty()){
                        while(true){
                            Log.d(TAG, "Content = "+content+", Interval = "+ time+ "ms");
                            Thread.sleep(time);
                        }
                    }
                } catch (InterruptedException e) {
                }

            });
            thread1.start();
        }else{
            thread1.interrupt();

            thread1 = new Thread(()->{
                try {
                    if(!content.isEmpty()){
                        while(true){
                            Log.d(TAG, "Content = "+content+", Interval = "+ time+ "ms");
                            Thread.sleep(time);
                        }
                    }
                } catch (InterruptedException e) {
                }
            });
            thread1.start();
        }
    }

    public void onClickServiceOffClicked(View view) {
        if(THrunning) {
            Log.d(TAG, "Stop Service");
            thread1.interrupt();
            THrunning=false;
        }
    }

    public void onClickCountOnClicked(View view) {
        int time = selected;

        if(!COrunning){
            thread2 = new Thread(()->{
                try {
                    COrunning=true;
                    count=0;
                    while(THrunning){
                        Log.d(TAG, "Count = "+(count++)+", Interval = "+ time+ "ms");
                        Thread.sleep(time);
                    }
                } catch (InterruptedException e) {
                }

            });
            thread2.start();
        }else{
            thread2.interrupt();

            thread2 = new Thread(()->{
                try {
                    count=0;
                    if(THrunning){
                        while(THrunning){
                            Log.d(TAG, "Count = "+(count++)+", Interval = "+ time+ "ms");
                            Thread.sleep(time);
                        }
                    }
                } catch (InterruptedException e) {
                }
            });
            thread2.start();
        }
    }

    public void onClickCountOffClicked(View view) {
        if(COrunning) {
            thread2.interrupt();
            COrunning=false;
        }
    }

}
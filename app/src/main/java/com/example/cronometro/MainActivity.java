package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    Button start, stop, reset;
    boolean isOn=false;
    TextView cronometro;
    Thread cronos;
    int mili=0, seg=0, minutos=0;
    Handler h= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=(Button) findViewById(R.id.start);
        stop=(Button) findViewById(R.id.stop);
        reset=(Button) findViewById(R.id.reset);
        cronometro=(TextView) findViewById(R.id.cronometro);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reset.setOnClickListener(this);
        cronos= new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (isOn){
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mili++;
                        if (mili==999){
                            seg++;
                            mili=0;
                        }
                        if (seg==59){
                            minutos++;
                            seg=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                String m="",s="",mi="";
                                if (mili<10){
                                    m="00"+mili;
                                } else if (mili<100) {
                                    m="0"+mili;
                                }else{
                                    m=""+mili;
                                }
                                if (seg<10){
                                    s="0"+seg;
                                }else {
                                    s=""+seg;
                                }
                                if (minutos<10){
                                    mi="0"+minutos;
                                }else {
                                    mi=""+minutos;
                                }
                                cronometro.setText(mi+":"+s+":"+m);
                            }
                        });
                    }
                }
            }
        });
        cronos.start();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.start:
                isOn=true;
                break;
            case R.id.stop:
                isOn=false;
                break;
            case R.id.reset:
                isOn=false;
                mili=0;
                seg=0;
                minutos=0;
                cronometro.setText("00:00:000");
                break;
        }
    }
}
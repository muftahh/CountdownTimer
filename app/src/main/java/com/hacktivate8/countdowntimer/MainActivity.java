package com.hacktivate8.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton btnAddCountdown;
    private volatile boolean stopCountdown = false;
    private TextInputEditText inputCountdown;
    private MaterialTextView tvCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCountdown = findViewById(R.id.btnAddCountdown);

        inputCountdown = findViewById(R.id.input_countdown);
        tvCountdown = findViewById(R.id.tv_countdown);

        btnAddCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCountdown.getText() == null) {
                    tvCountdown.setText("0");
                }
                String countdown = inputCountdown.getText().toString();
                tvCountdown.setText(countdown);
            }
        });

    }

    public void startCountdown(View view) {
        int intSecond = Integer.parseInt(tvCountdown.getText().toString());
        CountdownTimer countdownTimer = new CountdownTimer(intSecond);
        new Thread(countdownTimer).start();
    }

    public void stopCountdown(View view) {
        stopCountdown = true;
    }

    class CountdownTimer implements Runnable {

        int second;

        CountdownTimer (int second){
            this.second = second;
        }

        @Override
        public void run() {
            for (int i = 0; i <= second; i++){

                String newCount = Integer.toString(second - i);

                if (second-i == 0){
                    Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                    long[] pattern = {0, 200, 10, 500};
                    v.vibrate(pattern, 2);
                    if (v.hasVibrator()) {
                        System.out.println("YES");
                        Log.v("Can Vibrate", "YES");
                    } else {
                        Log.v("Can Vibrate", "NO");
                    }
                }

                Handler threadhadler =new Handler(Looper.getMainLooper());
                threadhadler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(newCount);
                        tvCountdown.setText(newCount);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
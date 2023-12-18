package com.example.conv_test_999;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class CourseActivity extends AppCompatActivity {
    private CurrencyConverter currencyConverter = new CurrencyConverter();
    private TextView uahTextView;
    private TextView eurTextView;
    private TextView rubTextView;
    private TextView usdTextView;
    private TextView cnyTextView;
    private TextView plnTextView;
    private ImageButton updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);



        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_LONG).show();
            // Отключите функционал приложения
            //    button.setEnabled(false);
            // или показать диалоговое окно с сообщением об ошибке
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка подключения")
                    .setMessage("Пожалуйста, проверьте ваше интернет-соединение")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        //end novoe


        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        uahTextView = findViewById(R.id.uahTextView);
        eurTextView = findViewById(R.id.eurTextView);
        rubTextView = findViewById(R.id.rubTextView);
        usdTextView = findViewById(R.id.usdTextView);
        cnyTextView = findViewById(R.id.cnyTextView);
        plnTextView = findViewById(R.id.plnTextView);
        updateButton = findViewById(R.id.updateButton);




        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateExchangeRates();
            }
        });

        updateExchangeRates();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void updateExchangeRates() {
        String[] currencies = {"UAH", "EUR", "RUB", "USD", "CNY", "PLN"};
        double[] amounts = {100, 1, 100, 1, 10, 10};
        TextView[] textViews = {uahTextView, eurTextView, rubTextView, usdTextView, cnyTextView, plnTextView};

        for (int i = 0; i < currencies.length; i++) {
            String from = currencies[i];
            double amount = amounts[i];
            TextView textView = textViews[i];

            currencyConverter.convert(from, amount, new CurrencyConverter.Callback() {
                @Override
                public void onResult(Map<String, Double> results) {
                    // Обновите пользовательский интерфейс с новыми данными о валютах
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (results.containsKey("BYN")) {
                                double result = results.get("BYN");
                                textView.setText(amount + " " + from + " is " + String.format("%.3f", result) + " BYN");
                                Animator animator = AnimatorInflater.loadAnimator(CourseActivity.this, R.animator.fade_in);
                                animator.setTarget(textView);
                                animator.start();
                            }
                        }
                    });
                }
            });
        }
    }
}
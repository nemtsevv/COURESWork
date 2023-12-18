package com.example.conv_test_999;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText editText_result_uah;
    private EditText editText_result_byn;
    private EditText editText_result_eur;
    private EditText editText_result_rub;
    private EditText editText_result_usd;
    private EditText editText_result_cny;
    private EditText editText_result_pln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка подключения")
                    .setMessage("Пожалуйста, проверьте ваше интернет-соединение")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


        // кнопка перехода на другую activity

        Button button = (Button) findViewById(R.id.myButton);
        ImageButton infButton = (ImageButton) findViewById(R.id.infButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CourseActivity.class);
                startActivity(intent);
            }
        });

        infButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Info...")
                        .setMessage("Приложение создано в рамках курсового проекта студентом физ.факультета группы МС-32 Немцевым Виталием. ГГУ, 2023г.")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });






// РАБОЧЕЕ

        editText_result_uah = findViewById(R.id.editText_result_uah);
        editText_result_byn = findViewById(R.id.editText_result_byn);
        editText_result_eur = findViewById(R.id.editText_result_eur);
        editText_result_rub = findViewById(R.id.editText_result_rub);
        editText_result_usd = findViewById(R.id.editText_result_usd);
        editText_result_cny = findViewById(R.id.editText_result_cny);
        editText_result_pln = findViewById(R.id.editText_result_pln);

        CurrencyConverter converter = new CurrencyConverter();

        addCurrencyTextWatcher(editText_result_uah, "UAH", converter);
        addCurrencyTextWatcher(editText_result_byn, "BYN", converter);
        addCurrencyTextWatcher(editText_result_eur, "EUR", converter);
        addCurrencyTextWatcher(editText_result_rub, "RUB", converter);
        addCurrencyTextWatcher(editText_result_usd, "USD", converter);
        addCurrencyTextWatcher(editText_result_cny, "CNY", converter);
        addCurrencyTextWatcher(editText_result_pln, "PLN", converter);

    }
    //novoe
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //end novoe
    private void addCurrencyTextWatcher(EditText editText, String currency, CurrencyConverter converter) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && editText.hasFocus()) {
                    double value = Double.parseDouble(s.toString());
                    converter.convert(currency, value, new CurrencyConverter.Callback() {
                        @Override
                        public void onResult(Map<String, Double> results) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!editText_result_uah.hasFocus()) {
                                        editText_result_uah.setText(String.format("%.3f", results.get("UAH")));
                                    }
                                    if (!editText_result_byn.hasFocus()) {
                                        editText_result_byn.setText(String.format("%.3f", results.get("BYN")));
                                    }
                                    if (!editText_result_eur.hasFocus()) {
                                        editText_result_eur.setText(String.format("%.3f", results.get("EUR")));
                                    }
                                    if (!editText_result_rub.hasFocus()) {
                                        editText_result_rub.setText(String.format("%.3f", results.get("RUB")));
                                    }
                                    if (!editText_result_usd.hasFocus()) {
                                        editText_result_usd.setText(String.format("%.3f", results.get("USD")));
                                    }
                                    if (!editText_result_cny.hasFocus()) {
                                        editText_result_cny.setText(String.format("%.3f", results.get("CNY")));
                                    }
                                    if (!editText_result_pln.hasFocus()) {
                                        editText_result_pln.setText(String.format("%.3f", results.get("PLN")));
                                    }
                                }
                            });
                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}

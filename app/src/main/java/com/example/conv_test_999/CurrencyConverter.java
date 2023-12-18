package com.example.conv_test_999;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.*;

class CurrencyResponse {
    String base;
    Map<String, Double> rates;
}

public class CurrencyConverter {
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    public void convert(String from, double amount, Callback callback) {
        Request request = new Request.Builder()
                .url("https://api.exchangerate-api.com/v4/latest/" + from)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    CurrencyResponse currencyResponse = gson.fromJson(response.body().string(), CurrencyResponse.class);
                    Map<String, Double> rates = currencyResponse.rates;
                    if (rates != null) {
                        Map<String, Double> results = new HashMap<>();
                        for (String currency : rates.keySet()) {
                            double rate = rates.get(currency);
                            double result = amount * rate;
                            results.put(currency, result);
                        }
                        callback.onResult(results);
                    }
                }
            }
        });
    }

    interface Callback {
        void onResult(Map<String, Double> results);
    }
}

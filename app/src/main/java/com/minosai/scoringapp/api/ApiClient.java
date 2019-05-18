package com.minosai.scoringapp.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.minosai.scoringapp.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://ec2-3-93-140-244.compute-1.amazonaws.com:5000/";

    public static ApiService apiService = null;

    public static ApiService getApiService(Context context) {

        if (apiService == null) {
            buildApiClient(context);
        }

        return apiService;
    }

    private static void buildApiClient(Context context) {

        String token = getToken(context);
        OkHttpClient okHttpClient = getOkHttpClient(token);

        apiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    private static String getToken(Context context) {

        SharedPreferences preferences = context
                .getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

        // TODO: Sample token for testing purposes
        return preferences.getString(Constants.PREF_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbXBfaWQiOiIxMjIyIiwiaWF0IjoxNTU4MTc2MjcxfQ._mGZJlZZyrSNDBgFbBzQiqcxd0ty0DbsxkVgz7A9r4w");
    }

    private static OkHttpClient getOkHttpClient(String token) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("x-access-token", token);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }
}

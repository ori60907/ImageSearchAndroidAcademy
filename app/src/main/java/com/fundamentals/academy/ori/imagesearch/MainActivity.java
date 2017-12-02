package com.fundamentals.academy.ori.imagesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import java.net.ResponseCache;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    static final private String pixabary_api_key = "7251510-2163e90b412235adef065d60e";
    private static final String TAG = "MainActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PixabayServiceGenerator service_generator = new PixabayServiceGenerator();
        PixabayServiceGenerator.PixabayService pixabay_service = service_generator.createService();

        retrofit2.Call<ImageSearchResponse> image_search_call = pixabay_service.searchImages(
                "flowers", "photo"
        );

        image_search_call.enqueue(new Callback<ImageSearchResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                ImageSearchResponse data = response.body();
                Log.i(TAG, data.getTotalHits().toString());
            }

            @Override
            public void onFailure(retrofit2.Call<ImageSearchResponse> call, Throwable t) {
                Log.e(TAG, "Getting images data failed!");
                t.printStackTrace();

            }
        });
    }
}

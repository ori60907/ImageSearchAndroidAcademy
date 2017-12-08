package com.fundamentals.academy.ori.imagesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    static final private String pixabary_api_key = "7251510-2163e90b412235adef065d60e";
    private static final String TAG = "MainActivityLog";
    private RecyclerView imagesFeedRV;
    private ImageFeedAdapter imageFeedAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ImageResult> imagesData;
    private PixabayServiceGenerator service_generator;
    private PixabayServiceGenerator.PixabayService pixabay_service;
    private Button searchButton;
    private TextView searchTV;

    private void initRecyclerView(){
        imagesFeedRV = findViewById(R.id.images_feed);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        imagesData = new ArrayList<ImageResult>();

        imageFeedAdapter = new ImageFeedAdapter(imagesData, this);
        imagesFeedRV.setAdapter(imageFeedAdapter);
        imagesFeedRV.setLayoutManager(layoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();

        searchButton = findViewById(R.id.search_button);
        searchTV = (TextView) findViewById(R.id.search_tv);

        service_generator = new PixabayServiceGenerator();
        pixabay_service = service_generator.createService();

        searchButton.setOnClickListener(new searchBTClickListener());
    }

    private class searchBTClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String search_text = searchTV.getText().toString();

            if (Objects.equals(search_text, "")){
                Toast.makeText(getBaseContext(), "Enter text first.", Toast.LENGTH_SHORT);
            }

            retrofit2.Call<ImageSearchResponse> image_search_call = pixabay_service.searchImages(
                    search_text, "photo"
            );

            image_search_call.enqueue(new Callback<ImageSearchResponse>() {
                @Override
                public void onResponse(retrofit2.Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                    ImageSearchResponse data = response.body();
                    Log.i(TAG, data.getTotalHits().toString());

                    List<Hit> result_list = data.getHits();
                    imageFeedAdapter.updateImagesFromHitsList(result_list);
                }

                @Override
                public void onFailure(retrofit2.Call<ImageSearchResponse> call, Throwable t) {
                    Log.e(TAG, "Getting images data failed!");
                    t.printStackTrace();
                    Toast.makeText(getBaseContext(), "We're sorry :( Search failed.", Toast.LENGTH_SHORT);
                }
            });
        }
    }
}

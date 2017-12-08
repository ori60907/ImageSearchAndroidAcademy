package com.fundamentals.academy.ori.imagesearch;

import java.util.Collections;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 02/12/2017.
 */

public class PixabayServiceGenerator {
    static final String BASE_URL = "https://pixabay.com/api/";
    static final String PIXABAY_API_KEY = "7251510-2163e90b412235adef065d60e";
    static ConnectionSpec spec = new
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
            .build();

    static OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Collections.singletonList(spec))
            .build();
    //static OkHttpClient.Builder http_client = new OkHttpClient.Builder();

    static Retrofit.Builder retrofit_builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create());
    static Retrofit retrofit = retrofit_builder.build();

    public PixabayService createService(){
        return retrofit.create(PixabayService.class);
    }

    public interface PixabayService{

        /** Basic search image query **/
        @GET("?key="+PIXABAY_API_KEY)
        Call<ImageSearchResponse> searchImages(
                @Query("q") String search_query,
                @Query("image_type") String image_type
        );

    }
}

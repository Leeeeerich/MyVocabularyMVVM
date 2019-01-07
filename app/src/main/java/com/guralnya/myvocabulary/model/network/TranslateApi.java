package com.guralnya.myvocabulary.model.network;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class TranslateApi {

    public static final String BASE_URL = "https://translate.googleapis.com/translate_a/";
    public static String client = "gtx";
    public static String dt = "t";
    private static Retrofit retrofit = null;

    public interface ApiInterface {
        @GET("single")
        Call<?> getTranslate(
                @Query("client") String client,
                @Query("sl") String originalLanguage,
                @Query("tl") String translationLanguage,
                @Query("dt") String dt,
                @Query("q") String query
        );
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

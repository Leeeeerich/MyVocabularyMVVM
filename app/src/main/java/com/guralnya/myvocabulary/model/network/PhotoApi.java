package com.guralnya.myvocabulary.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guralnya.myvocabulary.model.dto.cover_words_objects.CoverWordObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class PhotoApi {

    public static final String BASE_URL = "https://api.flickr.com/services/";

    public static final String SAFE_SEARCH = "safe";
    public static final String API_KEY = "1ac9a81715937cccec1b4d49463f3355";
    public static final String SORT = "relevance";
    public static final String METHOD = "flickr.photos.search";
    public static final String PER_PAGE = "10";
    public static final String MEDIA = "photos";
    public static final String EXTRAS = "url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    public static final String LICENSE = "1,2,3,4,5,6";
    public static final String FORMAT = "json";
    public static final String NOJSONCALLBACK = "1";

    private static Retrofit retrofit = null;

    public interface ApiInterface {

        /**
         * https://www.flickr.com/services/api/flickr.photos.search.html
         */
        @Headers("User-Agent: Chrome/60.0.3112.113")
        @GET("rest")
        Call<CoverWordObject> getPhotosList(
                @Query("safe_search") String safeSearch,
                @Query("api_key") String apiKey,
                @Query("sort") String sort,
                @Query("method") String method,
                @Query("per_page") String perPage,
                @Query("media") String media,
                @Query("extras") String extras,
                @Query("license") String license,
                @Query("format") String format,
                @Query("nojsoncallback") String nojsoncallback,
                @Query("text") String query
        );
    }

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

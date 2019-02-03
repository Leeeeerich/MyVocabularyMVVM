package com.guralnya.myvocabulary.model.network;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class TranslateApi {

    public static final String BASE_URL = "https://translate.googleapis.com/translate_a/";

    public static final String CLIENT = "gtx";
    public static final String TRANSLATE_LANGUAGE = "EN";
    public static final String TRANSLATED_LANGUAGE = "RU";
    public static final String DT = "t";
    public static final String DT_2 = "rm";

    private static Retrofit retrofit = null;

    public interface ApiInterface {

        /**
         * sl - source language code (auto for autodetection)
         * tl - translation language
         * q - source text / word
         * ie - input encoding (a guess)
         * oe - output encoding (a guess)
         * DT - may be included more than once and specifies what to return in the reply.
         * <p>
         * Here are some values for "DT" If the value is set, the following data will be returned:
         * <p>
         * t - translation of source text
         * at - alternate translations
         * rm - transcription / transliteration of source and translated texts
         * bd - dictionary, in case source text is one word (you get translations with articles, reverse translations, etc.)
         * md - definitions of source text, if it's one word
         * ss - synonyms of source text, if it's one word
         * ex - examples
         * rw - See also list.
         */
        @Headers("User-Agent: Chrome/60.0.3112.113")
        @GET("single")
        Call<JsonArray> getTranslate(
                @Query("client") String client,
                @Query("sl") String originalLanguage,
                @Query("tl") String translationLanguage,
                @Query("dt") String dt,
                @Query("dt") String dt2,
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

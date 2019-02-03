package com.guralnya.myvocabulary.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonArray;
import com.guralnya.myvocabulary.model.dto.DictionaryWord;
import com.guralnya.myvocabulary.model.network.TranslateApi;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslateRepository {

    private static volatile TranslateRepository instance;
    private MutableLiveData<DictionaryWord> mDictionaryWordLive;

    public static TranslateRepository getInstance() {
        TranslateRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (TranslateRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TranslateRepository();
                }
            }
        }
        return localInstance;
    }

    public MutableLiveData<DictionaryWord> getDictionaryWord(@NonNull String queryWord) {
        if (mDictionaryWordLive == null) {
            mDictionaryWordLive = new MutableLiveData<>();
        }

        Realm realm = Realm.getDefaultInstance();
        DictionaryWord results = realm.where(DictionaryWord.class)
                .equalTo("wordName", queryWord)
                .findFirst();

        if (results != null) {
            if (!results.isFull()) {
                getTranslate(queryWord);
            }
            mDictionaryWordLive.postValue(results);
            Log.d(getClass().getName(), "From dataBase");
        } else {
            getTranslate(queryWord);
            Log.d(getClass().getName(), "From internet");
        }
        realm.close();
        return mDictionaryWordLive;
    }

    private void getTranslate(String queryWord) {

        Call<JsonArray> call = TranslateApi.getClient()
                .create(TranslateApi.ApiInterface.class)
                .getTranslate(
                        TranslateApi.CLIENT,
                        TranslateApi.TRANSLATE_LANGUAGE,
                        TranslateApi.TRANSLATED_LANGUAGE,
                        TranslateApi.DT,
                        TranslateApi.DT_2,
                        queryWord
                );

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                Log.d(getClass().getName(), "Response = " + response.body());

                if (response.body() == null) return;
                DictionaryWord dictionaryWord = new DictionaryWord(
                        queryWord,
                        response.body().get(0).getAsJsonArray().get(1).getAsJsonArray().size() <= 3 ? "" : response.body().get(0).getAsJsonArray().get(1).getAsJsonArray().get(3).getAsString(),
                        response.body().get(0).getAsJsonArray().get(0).getAsJsonArray().get(0).getAsString()
                );

                dictionaryWord.setRating(0);
                dictionaryWord.setFull(true);
                mDictionaryWordLive.postValue(dictionaryWord);

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(dictionaryWord);
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                Log.e(getClass().getName(), "Error = " + t);
            }
        });
    }

    public void saveCoverWord(String url) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        DictionaryWord dictionaryWord = mDictionaryWordLive.getValue();
        dictionaryWord.setUrlImage(url);
        realm.copyToRealmOrUpdate(dictionaryWord);
        realm.commitTransaction();
        realm.close();
    }

    public void notAddWord(boolean bool) {
        if (bool) {
            if (mDictionaryWordLive.getValue() != null) {
                Realm realm = Realm.getDefaultInstance();

                final RealmResults<DictionaryWord> results = realm
                        .where(DictionaryWord.class)
                        .equalTo("wordName", mDictionaryWordLive.getValue().getWordName())
                        .findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // Delete all matches
                        results.deleteAllFromRealm();
                    }
                });
            }
        }
    }
}

package com.guralnya.myvocabulary.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.guralnya.myvocabulary.model.dto.cover_words_objects.CoverWordObject;
import com.guralnya.myvocabulary.model.network.PhotoApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoRepository {

    private static volatile PhotoRepository instance;
    private MutableLiveData<CoverWordObject> mCoverWordObjectMutableLiveData;

    public static PhotoRepository getInstance() {
        PhotoRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (PhotoRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PhotoRepository();
                }
            }
        }
        return localInstance;
    }

    public MutableLiveData<CoverWordObject> getCoversWord(@NonNull String queryWord) {
        if (mCoverWordObjectMutableLiveData == null) {
            mCoverWordObjectMutableLiveData = new MutableLiveData<>();
        }

        getTranslate(queryWord);
        return mCoverWordObjectMutableLiveData;
    }

    private void getTranslate(String queryWord) {

        Call<CoverWordObject> call = PhotoApi.getClient()
                .create(PhotoApi.ApiInterface.class)
                .getPhotosList(
                        PhotoApi.SAFE_SEARCH,
                        PhotoApi.API_KEY,
                        PhotoApi.SORT,
                        PhotoApi.METHOD,
                        PhotoApi.PER_PAGE,
                        PhotoApi.MEDIA,
                        PhotoApi.EXTRAS,
                        PhotoApi.LICENSE,
                        PhotoApi.FORMAT,
                        PhotoApi.NOJSONCALLBACK,
                        queryWord
                );

        call.enqueue(new Callback<CoverWordObject>() {
            @Override
            public void onResponse(@NonNull Call<CoverWordObject> call, @NonNull Response<CoverWordObject> response) {
                Log.d(getClass().getName(), "Response = " + response.body());

                if (response.body() == null) return;
                mCoverWordObjectMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                Log.e(getClass().getName(), "Error = " + t);
            }
        });
    }
}

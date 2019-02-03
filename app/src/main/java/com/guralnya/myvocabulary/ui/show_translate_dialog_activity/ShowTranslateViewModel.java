package com.guralnya.myvocabulary.ui.show_translate_dialog_activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.guralnya.myvocabulary.model.PhotoRepository;
import com.guralnya.myvocabulary.model.TranslateRepository;
import com.guralnya.myvocabulary.model.dto.DictionaryWord;
import com.guralnya.myvocabulary.model.dto.cover_words_objects.CoverWordObject;

public class ShowTranslateViewModel extends ViewModel {

    private MutableLiveData<DictionaryWord> mDictionaryWordLiveData;
    private MutableLiveData<CoverWordObject> mCoverWordObjectMutableLiveData;

    private TranslateRepository mTranslateRepository;
    private PhotoRepository mPhotoRepository;

    public ShowTranslateViewModel() {
        this.mTranslateRepository = TranslateRepository.getInstance();
        this.mPhotoRepository = PhotoRepository.getInstance();
    }

    public void setDictionaryWordLiveData(DictionaryWord dictionaryWordLiveData) {
        mDictionaryWordLiveData.setValue(dictionaryWordLiveData);
    }

    public LiveData<DictionaryWord> initDictionaryWord(String translateWord) {
        if (mDictionaryWordLiveData == null) {
            mDictionaryWordLiveData = new MutableLiveData<>();
            //getTranslate(translateWord);
        }
        setDictionaryWordLiveData(new DictionaryWord(translateWord, "", ""));
        mDictionaryWordLiveData = mTranslateRepository.getDictionaryWord(translateWord);
        return mDictionaryWordLiveData;
    }

    public MutableLiveData<DictionaryWord> getDictionaryWordLiveData() {
        return this.mDictionaryWordLiveData;
    }

    public void notAddWord(boolean bool) {
        mTranslateRepository.notAddWord(bool);
    }

    public void saveCoverWord(String url) {
        mTranslateRepository.saveCoverWord(url);
    }

    public void setCoverWordObjectMutableLiveData(CoverWordObject coverWordObject) {
        mCoverWordObjectMutableLiveData.setValue(coverWordObject);
    }

    public LiveData<CoverWordObject> initCoverWordObject(String translateWord) {
        if (mCoverWordObjectMutableLiveData == null) {
            mCoverWordObjectMutableLiveData = new MutableLiveData<>();
        }

        mCoverWordObjectMutableLiveData = mPhotoRepository.getCoversWord(translateWord);
        return mCoverWordObjectMutableLiveData;
    }

    public MutableLiveData<CoverWordObject> getCoverWordObjectMutableLiveData() {
        return this.mCoverWordObjectMutableLiveData;
    }
}

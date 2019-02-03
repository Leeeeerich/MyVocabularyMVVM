package com.guralnya.myvocabulary.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guralnya.myvocabulary.R;
import com.guralnya.myvocabulary.databinding.CardViewWordBinding;
import com.guralnya.myvocabulary.model.dto.DictionaryWord;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class CardsViewPagerAdapter extends PagerAdapter implements TextToSpeech.OnInitListener {

    private Context mContext;

    private Realm realm;
    private RealmResults<DictionaryWord> mDictionaryWordRealmResults;

    private TextToSpeech mTextToSpeech;
    private boolean mIsInit;

    public CardsViewPagerAdapter(Context context, RealmResults<DictionaryWord> dictionaryWordRealmResults) {
        this.mContext = context;
        this.mDictionaryWordRealmResults = dictionaryWordRealmResults;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        CardViewWordBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()), R.layout.card_view_word, container, false);

        final DictionaryWord dictionaryWord = mDictionaryWordRealmResults.get(position);
        if (dictionaryWord == null) {
            Log.d(getClass().getName(), "DictionaryWord = NULL");
            return binding;
        }
        Log.d(getClass().getName(), "DictionaryWord = " + dictionaryWord);
        binding.setWord(dictionaryWord);

        Glide.with(container)
                .load(dictionaryWord.getUrlImage())
                .apply(RequestOptions.centerInsideTransform())
                .into(binding.imCoverWord);

        mTextToSpeech = new TextToSpeech(mContext, this);
        binding.tvTranslateWord.setOnClickListener(v -> {
            if (mIsInit) {
                String textToSpeech = dictionaryWord.getWordName();
                mTextToSpeech.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, "id1");
            }
        });

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return mDictionaryWordRealmResults.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("eng");
            int result = mTextToSpeech.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                mIsInit = false;
            } else {
                mIsInit = true;
            }
        } else {
            mIsInit = false;
        }
        Log.d(getClass().getName(), "Initialization TTS = " + mIsInit);
    }
}

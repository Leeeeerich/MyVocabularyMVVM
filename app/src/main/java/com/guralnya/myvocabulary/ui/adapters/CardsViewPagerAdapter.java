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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guralnya.myvocabulary.MainActivity;
import com.guralnya.myvocabulary.R;
import com.guralnya.myvocabulary.databinding.CardViewWordBinding;
import com.guralnya.myvocabulary.model.dto.DictionaryWord;

import io.realm.RealmResults;

public class CardsViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private boolean mVisibilityTranslated = false;
    private RealmResults<DictionaryWord> mDictionaryWordRealmResults;

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

        binding.tvTranslateWord.setOnClickListener(v -> {
            String textToSpeech = dictionaryWord.getWordName();
            ((MainActivity) mContext).getTextToSpeech().speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, "id1");
        });

        binding.tvTranslatedWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVisibilityTranslated) {
                    ((TextView) v).setText(R.string.pls_press_for_view_translated);
                    mVisibilityTranslated = false;
                } else {
                    ((TextView) v).setText(dictionaryWord.getWordTranslate());
                    mVisibilityTranslated = true;
                }
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
}

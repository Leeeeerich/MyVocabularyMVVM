package com.guralnya.myvocabulary.ui.show_translate_dialog_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.guralnya.myvocabulary.R;
import com.guralnya.myvocabulary.databinding.ActivityShowTranslateBinding;
import com.guralnya.myvocabulary.ui.adapters.ImageViewContainerAdapter;

import java.util.Locale;

public class ShowTranslateDialogActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ShowTranslateViewModel mViewModel;
    private ActivityShowTranslateBinding mBinding;

    private ViewPager mViewPager;
    private ImageViewContainerAdapter mImageViewContainerAdapter;

    private TextToSpeech mTextToSpeech;
    private boolean mIsInit;

    private boolean isNotAddWord = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_translate);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_translate);

        final CharSequence word = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

        mViewModel = ViewModelProviders.of(this).get(ShowTranslateViewModel.class);

        mViewModel.initDictionaryWord(word.toString().toLowerCase()).observe(this, dictionaryWord1 -> {
            mBinding.setWord(dictionaryWord1);
        });

        //Image carousel
        mViewPager = mBinding.viewPager;
        mViewModel.initCoverWordObject(word.toString().toLowerCase()).observe(this, coverWordObject -> {
            if (coverWordObject != null) {
                mImageViewContainerAdapter = new ImageViewContainerAdapter(
                        this, coverWordObject.getPhotos().getPhotoList());
                mViewPager.setAdapter(mImageViewContainerAdapter);
            }
        });

        mTextToSpeech = new TextToSpeech(this, this);
        mBinding.tvTranslateWord.setOnClickListener(v -> {
            if (mIsInit) {
                String textToSpeech = word.toString();
                mTextToSpeech.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, "id1");
            }
        });
    }

    public void onClickClose(View view) {
        finish();
    }

    public void onClickNotAddWord(View view) {
        isNotAddWord = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.notAddWord(isNotAddWord);
        if (!isNotAddWord) {
            mViewModel.saveCoverWord(
                    mImageViewContainerAdapter
                            .getImageList()
                            .get(mViewPager.getCurrentItem())
                            .getUrlM());
        }
        mTextToSpeech.shutdown();
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

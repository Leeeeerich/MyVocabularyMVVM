package com.guralnya.myvocabulary.ui.show_translate_dialog_activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.guralnya.myvocabulary.R;

import com.guralnya.myvocabulary.databinding.ActivityShowTranslateBinding;
import com.guralnya.myvocabulary.model.dto.DictionaryWord;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.internal.operators.single.SingleFromCallable;
import io.reactivex.schedulers.Schedulers;

public class ShowTranslateDialogActivity extends AppCompatActivity {

   // private ShowTranslateViewModel mViewModel;
    private ActivityShowTranslateBinding mBinding;
    DictionaryWord dictionaryWord;
    String translateWord = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_translate);

        //mViewModel = ViewModelProviders.of(this).get(ShowTranslateViewModel.class);

        final CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        dictionaryWord = new DictionaryWord(text.toString(), "", "");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_translate);
        mBinding.setWord(dictionaryWord);



        /*
        Observable<String> yob = new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                Log.e("URLConnection", "RxJava uhu");
                try {
                    translateWord = callUrlAndParseResult("en", "ru", text.toString());
                } catch (Exception e) {
                    Log.e("URLConnection", "URL Connection Error = " + e);
                    e.printStackTrace();
                }
                Log.e("URLConnection", "translateWord = " + translateWord);
                dictionaryWord.setWordTranslate(translateWord);
                mBinding.invalidateAll();
            }
        };

        yob.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single()).subscribe();
*/

    }

    private String callUrlAndParseResult(String langFrom, String langTo,
                                         String word) throws Exception
    {

        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
    }

    private String parseResult(String inputJson) throws Exception
    {
        /*
         * inputJson for word 'hello' translated to language Hindi from English-
         * [[["नमस्ते","hello",,,1]],,"en"]
         * We have to get 'नमस्ते ' from this json.
         */

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return jsonArray3.get(0).toString();
    }


}

package com.guralnya.myvocabulary.model.dto;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DictionaryWord extends RealmObject implements Serializable {

    private int id;
    @PrimaryKey
    private String wordName;
    private String rootOfTheWord;
    private String wordTranscription;
    private String wordTranslate;
    private String urlImage;
    private boolean missTheWorld;
    private int rating;
    private boolean isFull;

    public DictionaryWord() {
    }

    public DictionaryWord(String wordName, String wordTranscription, String wordTranslate) {
        this.wordName = wordName;
        this.wordTranscription = wordTranscription;
        this.wordTranslate = wordTranslate;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getWordTranscription() {
        return wordTranscription;
    }

    public void setWordTranscription(String wordTranscription) {
        this.wordTranscription = wordTranscription;
    }

    public String getWordTranslate() {
        return wordTranslate;
    }

    public void setWordTranslate(String wordTranslate) {
        this.wordTranslate = wordTranslate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isMissTheWorld() {
        return missTheWorld;
    }

    public void setMissTheWorld(boolean missTheWorld) {
        this.missTheWorld = missTheWorld;
    }

    public String getRootOfTheWord() {
        return rootOfTheWord;
    }

    public void setRootOfTheWord(String rootOfTheWord) {
        this.rootOfTheWord = rootOfTheWord;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}

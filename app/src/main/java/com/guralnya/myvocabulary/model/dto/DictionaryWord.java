package com.guralnya.myvocabulary.model.dto;

public class DictionaryWord {

    //@PrimaryKey
    private int id;

    private String wordName;
    private String rootOfTheWord;
    private String wordTranscription;
    private String wordTranslate;
    private boolean missTheWorld;

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
}

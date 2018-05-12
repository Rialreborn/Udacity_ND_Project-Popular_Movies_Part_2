package com.example.android.popularmoviesapppart2.Model;

public class Reviews {

    private String mAuthor;
    private String mContent;

    public Reviews(String author, String content) {
        this.mAuthor = author;
        this.mContent = content;
    }

    public String getAuthor() {return mAuthor;}

    public String getContent() {return mContent;}


}

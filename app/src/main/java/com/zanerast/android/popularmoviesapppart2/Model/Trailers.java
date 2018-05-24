package com.zanerast.android.popularmoviesapppart2.Model;


public class Trailers {

    private String mKey;
    private String mTrailerName;

    public Trailers(String key, String trailerName){
        this.mKey = key;
        this.mTrailerName = trailerName;
    }

    public String getKey() {return mKey;}

    public String getTrailerName() {return mTrailerName;}

}

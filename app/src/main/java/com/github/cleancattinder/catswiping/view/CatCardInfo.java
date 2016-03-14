package com.github.cleancattinder.catswiping.view;

/**
 * The ViewModel for the CatCardsView.
 * Just enough info for a brief overview of a Cat.
 */
public class CatCardInfo {
    public final String id;
    public final String imageUrl;
    public final String title;

    public CatCardInfo(String id, String imageUrl, String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
    }
}

package com.github.cleancattinder.catswiping.interactor;

import com.github.cleancattinder.catswiping.view.CatCardInfo;

import java.util.List;

import rx.Observable;

public interface CatSwipeInteractor {

    Observable<List<CatCardInfo>> getCats(int page);

    void likeCat(String id);

    void dislikeCat(String id);
}

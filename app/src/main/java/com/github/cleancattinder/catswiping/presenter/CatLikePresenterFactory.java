package com.github.cleancattinder.catswiping.presenter;

import com.github.cleancattinder.catswiping.interactor.google.GoogleCatLikeInteractor;
import com.github.cleancattinder.catswiping.interactor.imgur.ImgurCatLikeInteractor;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.imageservice.google.GoogleService;
import com.github.cleancattinder.imageservice.imgur.ImgurService;
import com.github.cleancattinder.rx.SchedulerFactoryImpl;

public class CatLikePresenterFactory {

    public CatLikePresenter presentImagesFromGoogle(CatCardsView view) {
        return new CatLikePresenterImpl(
            view,
            new GoogleCatLikeInteractor(new GoogleService()),
            new SchedulerFactoryImpl()
        );
    }

    public CatLikePresenter presentImagesFromImgur(CatCardsView view) {
        return new CatLikePresenterImpl(
            view,
            new ImgurCatLikeInteractor(new ImgurService()),
            new SchedulerFactoryImpl()
        );
    }

}

package com.github.cleancattinder.catswiping.presenter;

import com.github.cleancattinder.catswiping.interactor.google.GoogleCatLikeInteractor;
import com.github.cleancattinder.catswiping.interactor.imgur.ImgurCatLikeInteractor;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.imageservice.google.GoogleService;
import com.github.cleancattinder.imageservice.imgur.ImgurService;
import com.github.cleancattinder.rx.SchedulerFactory;

public class CatLikePresenterFactory {

    public CatLikePresenter presentImagesFromGoogle(CatCardsView view, SchedulerFactory sf) {
        return new CatLikePresenterImpl(
            view,
            new GoogleCatLikeInteractor(new GoogleService()),
            sf
        );
    }

    public CatLikePresenter presentImagesFromImgur(CatCardsView view, SchedulerFactory sf) {
        return new CatLikePresenterImpl(
            view,
            new ImgurCatLikeInteractor(new ImgurService()),
            sf
        );
    }

}

package com.github.cleancattinder.catswiping.presenter;


import com.github.cleancattinder.catswiping.interactor.CatLikeInteractor;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.pagination.IntPaginator;
import com.github.cleancattinder.rx.SchedulerFactory;

import java.util.List;

import rx.Subscriber;
import timber.log.Timber;

/**
 * Contains the business logic around loading, liking, and disliking cats.
 *
 * Note that we are referencing interfaces here NOT implementations.
 *
 * This means I can reuse this class and pass in different implementations of
 * the CatLikeInteractor to change the way data is obtained. The presenter, and the view
 * are agnostic when it comes to knowing where the data comes from.
 *
 * Also, we can change the presentation by passing in different implementations of the View.
 */
class CatLikePresenterImpl implements CatLikePresenter {

    private final CatCardsView catCardsView;
    private final CatLikeInteractor mCatLikeInteractor;
    private final SchedulerFactory schedulers;

    public CatLikePresenterImpl(CatCardsView catCardsView, CatLikeInteractor catLikeInteractor, SchedulerFactory
        schedulers) {
        this.catCardsView = catCardsView;
        this.mCatLikeInteractor = catLikeInteractor;
        this.schedulers = schedulers;

    }

    @Override
    public void loadCats() {
        Timber.d("Loading cats...");
        mCatLikeInteractor.getCats()
                          .subscribeOn(schedulers.io())
                          .observeOn(schedulers.mainThread())
                          .subscribe(new Subscriber<List<CatCardInfo>>() {
                              @Override
                              public void onCompleted() {
                                  Timber.d("Done loading cats");
                              }

                              @Override
                              public void onError(Throwable e) {
                                  Timber.e(e.getMessage());
                              }

                              @Override
                              public void onNext(List<CatCardInfo> catCardInfos) {
                                  catCardsView.addCards(catCardInfos);
                              }
                          });
    }

    @Override
    public void onCatLiked(String id) {
        mCatLikeInteractor.likeCat(id);
    }

    @Override
    public void onCatDisliked(String id) {
        mCatLikeInteractor.dislikeCat(id);
    }
}

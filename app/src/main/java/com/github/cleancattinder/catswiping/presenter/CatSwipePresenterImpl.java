package com.github.cleancattinder.catswiping.presenter;


import com.github.cleancattinder.catswiping.interactor.CatSwipeInteractor;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.pagination.IntPaginator;
import com.github.cleancattinder.rx.SchedulerFactory;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;
import timber.log.Timber;

/**
 * Contains the business logic around loading, liking, and disliking cats.
 * Note that we are referencing interfaces here NOT implementations.
 */
public class CatSwipePresenterImpl implements CatSwipePresenter {

    private final CatCardsView catCardsView;
    private final CatSwipeInteractor catSwipeInteractor;
    private final SchedulerFactory schedulers;
    private final IntPaginator paginator;

    public CatSwipePresenterImpl(CatCardsView catCardsView, CatSwipeInteractor catSwipeInteractor, SchedulerFactory
        schedulers) {
        this.catCardsView = catCardsView;
        this.catSwipeInteractor = catSwipeInteractor;
        this.schedulers = schedulers;
        this.paginator = new IntPaginator(/* startPage */ 1, /* pageStep */ 1);
    }

    @Override
    public void loadCats() {
        Timber.d("Loading cats...");
        catSwipeInteractor.getCats(paginator.getNextPage())
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
        catSwipeInteractor.likeCat(id);
    }

    @Override
    public void onCatDisliked(String id) {
        catSwipeInteractor.dislikeCat(id);
    }
}

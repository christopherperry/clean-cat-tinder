package com.github.cleancattinder.catswiping.interactor.google;

import com.github.cleancattinder.catswiping.interactor.CatLikeInteractor;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.imageservice.google.GoogleImageSearchItem;
import com.github.cleancattinder.imageservice.google.GoogleImageSearchResponse;
import com.github.cleancattinder.imageservice.google.GoogleService;
import com.github.cleancattinder.pagination.IntPaginator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

public class GoogleCatLikeInteractor implements CatLikeInteractor {

    private final GoogleService googleService;
    private final Map<String, String> queryParams;
    private final IntPaginator paginator;

    public GoogleCatLikeInteractor(GoogleService googleService) {
        this.googleService = googleService;
        this.queryParams = new HashMap<>();
        this.queryParams.put("q", "cat");
        this.queryParams.put("key", "AIzaSyAZmvW6DecHAvtLCiqYQzmhKCNnsOYxtgo");
        this.queryParams.put("cx", "005351716643766109453:uusxxukdams");
        this.paginator = new IntPaginator(/* startPage */ 1, /* pageStep */ 10);
    }

    @Override
    public Observable<List<CatCardInfo>> getCats() {
        return googleService.imageSearch(paginator.getNextPage(), queryParams)
                            .flatMap(toSearchItemObservable)
                            .map(toCatCardInfo)
                            .toList();
    }

    @Override
    public void likeCat(String id) {
        // TODO: ??? Items don't have IDs
    }

    @Override
    public void dislikeCat(String id) {
        // TODO: ??? Items don't have IDs
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Mapping Functions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Func1<GoogleImageSearchResponse, Observable<GoogleImageSearchItem>> toSearchItemObservable =
        googleImageSearchResponse -> Observable.from(googleImageSearchResponse.items);

    private Func1<GoogleImageSearchItem, CatCardInfo> toCatCardInfo =
        googleImageSearchItem -> new CatCardInfo("N/A", googleImageSearchItem.link, googleImageSearchItem.title);
}

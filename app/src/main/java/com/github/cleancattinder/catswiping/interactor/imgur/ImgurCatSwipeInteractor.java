package com.github.cleancattinder.catswiping.interactor.imgur;

import com.github.cleancattinder.catswiping.interactor.CatSwipeInteractor;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.imgur.ImgurGalleryItem;
import com.github.cleancattinder.imgur.ImgurGalleryResponse;
import com.github.cleancattinder.imgur.ImgurService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Gets cat images from Imgur.
 *
 * Since it would take extra calls and logic here to drill
 * down on responses that represent albums in order to get an image to show,
 * we filter out albums from the response. We can add these in later.
 */
public class ImgurCatSwipeInteractor implements CatSwipeInteractor {

    private final ImgurService imgurService;
    private final Map<String, String> queryParams;

    public ImgurCatSwipeInteractor(ImgurService imgurService) {
        this.imgurService = imgurService;
        this.queryParams = new HashMap<>();
        this.queryParams.put("q", "title: cats ext: jpg OR png");
    }

    @Override
    public Observable<List<CatCardInfo>> getCats(int page) {
        return imgurService.gallerySearch(page, queryParams)
                           .flatMap(toGalleryItemObservable)
                           .filter(removeIfAlbum)
                           .map(toCatCardInfo)
                           .toList();
    }

    @Override
    public void likeCat(String id) {
        // TODO: ??? Save these somewhere ???
    }

    @Override
    public void dislikeCat(String id) {
        // TODO: ??? Save these somewhere and filter them from the search results ???
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Mapping Functions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Func1<ImgurGalleryItem, CatCardInfo> toCatCardInfo =
        imgurGalleryItem -> new CatCardInfo(imgurGalleryItem.id, imgurGalleryItem.link, imgurGalleryItem.title);

    private Func1<ImgurGalleryResponse, Observable<ImgurGalleryItem>> toGalleryItemObservable =
        imgurGallerySearchResponse -> Observable.from(imgurGallerySearchResponse.data);

    private Func1<ImgurGalleryItem, Boolean> removeIfAlbum = imgurGalleryItem -> !imgurGalleryItem.is_album;

}

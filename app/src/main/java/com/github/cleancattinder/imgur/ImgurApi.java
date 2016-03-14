package com.github.cleancattinder.imgur;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ImgurApi {

    /**
     * Try gallery search:
     * https://api.imgur.com/endpoints/gallery#gallery-search
     *
     * See here for sizing the thumbnails when you request them:
     * https://api.imgur.com/models/gallery_image
     */
    @Headers("Authorization: Client-ID 8931400a0875247")
    @GET("gallery/search/viral/{page}/search")
    Call<ImgurGalleryResponse> gallerySearch(@Path("page") int page, @QueryMap Map<String, String> queryMap);

    @Headers("Authorization: Client-ID 8931400a0875247")
    @GET("image/{id}")
    Call<ImgurGalleryResponse> getImage(@Path("id") int id);
}

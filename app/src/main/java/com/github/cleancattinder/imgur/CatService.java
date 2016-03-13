package com.github.cleancattinder.imgur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CatService {

    /**
     * Try gallery search:
     * https://api.imgur.com/endpoints/gallery#gallery-search
     *
     * dunno if that's right or not yet
     */
    @Headers("Authorization: Client-ID 8931400a0875247")
    @GET("")
    Call<List<ImgurCat>> getCats();
}

package com.github.cleancattinder.imageservice.google;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

interface GoogleApi {

    @GET("customsearch/v1?key=AIzaSyAZmvW6DecHAvtLCiqYQzmhKCNnsOYxtgo&cx=005351716643766109453" +
        ":uusxxukdams&searchType=image&q=cat")
    Call<GoogleImageSearchResponse> imageSearch(@Query("start") int startIndex, @QueryMap Map<String, String> queryMap);
}

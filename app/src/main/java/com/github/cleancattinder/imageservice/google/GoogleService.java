package com.github.cleancattinder.imageservice.google;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GoogleService {

    private static final String BASE_URL = "https://www.googleapis.com/";
    private final GoogleApi googleApi;

    public GoogleService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        googleApi = retrofit.create(GoogleApi.class);
    }

    public Observable<GoogleImageSearchResponse> imageSearch(int page, Map<String, String> queryMap) {
        return Observable.create(subscriber -> {
            try {
                final Call<GoogleImageSearchResponse> call = googleApi.imageSearch(page, queryMap);
                final Response<GoogleImageSearchResponse> response = call.execute();
                if (response.isSuccess()) {
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Exception("Problem getting images from Google: " + response.code()));
                }
            }
            catch (IOException e) {
               subscriber.onError(e);
            }
        });
    }
}

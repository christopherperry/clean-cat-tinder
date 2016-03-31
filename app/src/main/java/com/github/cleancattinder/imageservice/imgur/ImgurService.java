package com.github.cleancattinder.imageservice.imgur;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import java.io.IOException;
import java.util.Map;

public class ImgurService {
    private static final String BASE_URL = "https://api.imgur.com/3/";
    private final ImgurApi mImgurApi;

    public ImgurService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        mImgurApi = retrofit.create(ImgurApi.class);
    }

    public Observable<ImgurGalleryResponse> gallerySearch(int page, Map<String, String> queryMap) {
        return Observable.create(subscriber -> {
            try {
                final Call<ImgurGalleryResponse> call = mImgurApi.gallerySearch(page, queryMap);
                final Response<ImgurGalleryResponse> response = call.execute();
                if (response.isSuccess()) {

                    final ImgurGalleryResponse body = response.body();

                    subscriber.onNext(body);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Exception("Gallery Search Failed"));
                }
            }
            catch (IOException e) {
                subscriber.onError(new Exception("Gallery Search Failed", e));
            }
        });


    }
}

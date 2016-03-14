package com.github.cleancattinder.imgur;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import timber.log.Timber;

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
                Timber.d("Executing Imgur Gallery Search...");
                final Call<ImgurGalleryResponse> call = mImgurApi.gallerySearch(page, queryMap);
                final Response<ImgurGalleryResponse> response = call.execute();
                if (response.isSuccess()) {
                    Timber.d("Successful response! %s", response.raw());

                    Timber.d("Attempting to parse the body");
                    final ImgurGalleryResponse body = response.body();
                    Timber.d("Body has %d items!", body.data.size());

                    subscriber.onNext(body);
                    subscriber.onCompleted();
                } else {
                    Timber.e("Un-successful response!");
                    subscriber.onError(new Exception("Gallery Search Failed"));
                }
            }
            catch (IOException e) {
                subscriber.onError(new Exception("Gallery Search Failed", e));
            }
        });


    }
}

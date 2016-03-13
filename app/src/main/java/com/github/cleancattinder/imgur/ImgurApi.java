package com.github.cleancattinder.imgur;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class ImgurApi {
    private static final String BASE_URL = "https://api.imgur.com/3/";
    private final CatService catService;

    public ImgurApi() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        catService = retrofit.create(CatService.class);
    }

    public Observable<List<ImgurCat>> getCats() {
        return Observable.create(subscriber -> {
            try {
                final Call<List<ImgurCat>> call = catService.getCats();
                final Response<List<ImgurCat>> response = call.execute();
                if (response.isSuccess()) {
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Exception("Failed to get cats"));
                }
            }
            catch (IOException e) {
                subscriber.onError(new Exception("Failed to get cats", e));
            }
        });


    }
}

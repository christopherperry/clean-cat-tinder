package com.github.cleancattinder.catswiping;

import com.github.cleancattinder.R;
import com.github.cleancattinder.catswiping.presenter.CatSwipePresenter;
import com.github.cleancattinder.catswiping.presenter.CatSwipePresenterImpl;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.catswiping.interactor.imgur.ImgurCatSwipeInteractor;
import com.github.cleancattinder.imgur.ImgurService;
import com.github.cleancattinder.rx.SchedulerFactoryImpl;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Allows the user of the application to like/dislike cats by
 * swiping right to like a cat, and swiping left to dislike a cat.
 */
public class CatSwipeActivity extends AppCompatActivity implements CatCardsView, SwipeFlingAdapterView.onFlingListener {
    @Bind(R.id.catSwipeView) SwipeFlingAdapterView catSwipeView;

    private CatSwipePresenter catSwipePresenter;
    private CatSwipeAdapter catSwipeAdapter;

    // TODO: replace with Dagger
    void inject() {
        catSwipePresenter = new CatSwipePresenterImpl(this, new ImgurCatSwipeInteractor(new ImgurService()), new
            SchedulerFactoryImpl());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_swipe);
        ButterKnife.bind(this);
        inject();

        catSwipeAdapter = new CatSwipeAdapter(getLayoutInflater(), Picasso.with(this));
        catSwipeView.setAdapter(catSwipeAdapter);
        catSwipeView.setFlingListener(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CatCardsView Implementation
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void addCards(List<CatCardInfo> catCardInfos) {
        Timber.d("Adding %d cards!", catCardInfos.size());
        catSwipeAdapter.addCatCards(catCardInfos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Click Listeners
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @OnClick(R.id.yesButton)
    public void onYesClicked() {
        catSwipeView.getTopCardListener().selectRight();
    }

    @OnClick(R.id.noButton)
    public void onNoClicked() {
        catSwipeView.getTopCardListener().selectLeft();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SwipeFlingAdapterView.onFlingListener Implementation
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void removeFirstObjectInAdapter() {
        catSwipeAdapter.removeFirstCatCard();
    }

    @Override
    public void onLeftCardExit(Object o) {
//        catSwipePresenter.onCatDisliked();
    }

    @Override
    public void onRightCardExit(Object o) {
//        catSwipePresenter.onCatLiked();
    }

    @Override
    public void onAdapterAboutToEmpty(int i) {
        Timber.d("Adapter about to empty, loading more cats!");
        catSwipePresenter.loadCats();
    }

    @Override
    public void onScroll(float v) {
        // Negative values means left, positive means right
        View view = catSwipeView.getSelectedView();
        if (v < 0) {
            view.findViewById(R.id.no)
                .setAlpha(v * -0.8f);
        }
        else if (v > 0) {
            view.findViewById(R.id.yes)
                .setAlpha(v * 0.8f);
        }
        else {
            view.findViewById(R.id.no)
                .setAlpha(0);
            view.findViewById(R.id.yes)
                .setAlpha(0);
        }
    }
}

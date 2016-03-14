package com.github.cleancattinder.catswiping;

import com.github.cleancattinder.R;
import com.github.cleancattinder.catswiping.presenter.CatLikePresenter;
import com.github.cleancattinder.catswiping.presenter.CatLikePresenterFactory;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
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

    private static final String EXTRA_SERVICE_CHOICE = "service_choice";
    public static final int SERVICE_GOOGLE = 0;
    public static final int SERVICE_IMGUR = 1;

    @Bind(R.id.catSwipeView) SwipeFlingAdapterView catSwipeView;

    private CatLikePresenter catLikePresenter;
    private CatSwipeAdapter catSwipeAdapter;

    public static Intent getCallingIntent(Context context, int service) {
        final Intent intent = new Intent(context, CatSwipeActivity.class);
        intent.putExtra(EXTRA_SERVICE_CHOICE, service);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_swipe);
        ButterKnife.bind(this);

        catLikePresenter = getPresenter();

        catSwipeAdapter = new CatSwipeAdapter(getLayoutInflater(), Picasso.with(this));
        catSwipeView.setAdapter(catSwipeAdapter);
        catSwipeView.setFlingListener(this);
    }

    CatLikePresenter getPresenter() {
        final Intent intent = getIntent();
        final int service = intent.getIntExtra(EXTRA_SERVICE_CHOICE, SERVICE_GOOGLE);
        final CatLikePresenterFactory factory = new CatLikePresenterFactory();
        if (service == SERVICE_GOOGLE) {
            return factory.presentImagesFromGoogle(this);
        } else {
            return factory.presentImagesFromImgur(this);
        }
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
        CatCardInfo catCardInfo = (CatCardInfo)o;
        catLikePresenter.onCatDisliked(catCardInfo.id);
    }

    @Override
    public void onRightCardExit(Object o) {
        CatCardInfo catCardInfo = (CatCardInfo)o;
        catLikePresenter.onCatLiked(catCardInfo.id);
    }

    @Override
    public void onAdapterAboutToEmpty(int i) {
        Timber.d("Adapter about to empty, loading more cats!");
        catLikePresenter.loadCats();
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

package com.github.cleancattinder.catswiping.presenter;

/**
 * Handles the business rules around liking / disliking
 * cats.
 *
 * The business rules are:
 *
 * 1. Cat data should be paginated.
 * 2. User should be able to like a cat
 * 3. User should be able to dislike a cat
 */
public interface CatLikePresenter {

    void loadCats();

    void onCatLiked(String id);

    void onCatDisliked(String id);

}

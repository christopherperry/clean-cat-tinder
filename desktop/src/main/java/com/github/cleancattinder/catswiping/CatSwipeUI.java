package com.github.cleancattinder.catswiping;

import com.github.cleancattinder.catswiping.presenter.CatLikePresenter;
import com.github.cleancattinder.catswiping.presenter.CatLikePresenterFactory;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.rx.SchedulerFactory;

import java.awt.*;
import java.util.List;

import javax.swing.*;

public class CatSwipeUI extends JFrame implements CatCardsView, CardFlipAdapter.CardAdapterListener {

    private final CatLikePresenter presenter;
    private final CardFlipper cardFlipper;
    private final CardFlipAdapter adapter;

    private boolean hasShownFirstCat;

    public CatSwipeUI(boolean isGoogle, SchedulerFactory schedulerFactory) {
        super("Cat Tinder");
        setPreferredSize(new Dimension(600, 600));
        this.presenter = createPresenter(isGoogle, schedulerFactory);

        cardFlipper = new CardFlipper();
        adapter = new CardFlipAdapter(schedulerFactory, this);
        cardFlipper.setAdapter(adapter);
    }

    CatLikePresenter createPresenter(boolean isGoogle, SchedulerFactory schedulerFactory) {
        if (isGoogle) {
            return new CatLikePresenterFactory().presentImagesFromGoogle(this, schedulerFactory);
        }
        return new CatLikePresenterFactory().presentImagesFromImgur(this, schedulerFactory);
    }

    public void load() {
        presenter.loadCats();
    }

    public void addComponentsToPane(Container container) {
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());

        addCardLayout(mainContainer);
        addButtons(mainContainer);

        container.add(mainContainer);
    }

    private void addCardLayout(JPanel mainContainer) {

        final JPanel middlePanel = new JPanel(new GridLayout(3, 1));
        middlePanel.add(new JPanel());
        middlePanel.add(cardFlipper);
        middlePanel.add(new JPanel());

        JPanel topContainer = new JPanel(new GridLayout(1, 3));
        topContainer.add(new JPanel());
        topContainer.add(middlePanel);
        topContainer.add(new JPanel());

        mainContainer.add(topContainer, BorderLayout.CENTER);
    }

    private void addButtons(JPanel mainContainer) {
        JPanel buttonContainer = new JPanel(new GridLayout(1, 5));

        JButton likeButton = new JButton("Like");
        JButton dislikeButton = new JButton("Dislike");

        buttonContainer.add(new JPanel());
        buttonContainer.add(dislikeButton);
        buttonContainer.add(new JPanel());
        buttonContainer.add(likeButton);
        buttonContainer.add(new JPanel());

        likeButton.addActionListener(e -> {
            System.out.println("Like button clicked");
            cardFlipper.showNext();
        });

        dislikeButton.addActionListener(e -> {
            System.out.println("Dislike button clicked");
            cardFlipper.showNext();
        });

        // adds the buttons to the bottom
        mainContainer.add(buttonContainer, BorderLayout.SOUTH);
    }

    @Override
    public void onAdapterAboutToEmpty() {
        System.out.println("onAdapterAboutToEmpty(), loading more cats");
        presenter.loadCats();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CatCardsView Implementation
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addCards(List<CatCardInfo> catCardInfos) {
        System.out.println("Got more cats!");
        adapter.addCards(catCardInfos);
        if (!hasShownFirstCat) {
            System.out.println("Haven't shown first cat yet. Loading next card");
            hasShownFirstCat = true;
            cardFlipper.showFirst();
        }
    }
}

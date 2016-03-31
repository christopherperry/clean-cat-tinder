package com.github.cleancattinder.catswiping;

import com.github.cleancattinder.catswiping.presenter.CatLikePresenter;
import com.github.cleancattinder.catswiping.presenter.CatLikePresenterFactory;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.catswiping.view.CatCardsView;
import com.github.cleancattinder.rx.DesktopSchedulerFactory;
import rx.Observable;
import rx.Subscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CatSwipeUI extends JFrame implements CatCardsView {
    CardLayout cardLayout = new CardLayout();
    JPanel mainContainer = new JPanel();
    JPanel buttonContainer = new JPanel();
    ImagePanel cardOne = new ImagePanel();
    ImagePanel cardTwo = new ImagePanel();
    JPanel cardContainer = new JPanel();

    JButton likeButton = new JButton("Like");
    JButton dislikeButton = new JButton("Dislike");
    JPanel leftSpacer = new JPanel();
    JPanel centerSpacer = new JPanel();
    JPanel rightSpacer = new JPanel();

    JPanel currentCard = cardOne;

    private final List<CatCardInfo> catCardInfos = new ArrayList<>();
    private CatLikePresenter presenter;
    private boolean hasShownFirstCat;

    private CatSwipeUI() {
        super("Cat Tinder");
        setPreferredSize(new Dimension(600, 600));
        presenter = new CatLikePresenterFactory().presentImagesFromGoogle(this, new DesktopSchedulerFactory());

        presenter.loadCats();
        setupButtons();
    }

    void addComponentsToPane(Container container) {
        mainContainer.setLayout(new BorderLayout());

        setupCards();
        setupButtons();

        container.add(mainContainer);
    }

    private void setupCards() {
        cardContainer.setLayout(cardLayout);
        cardContainer.add(cardOne);
        cardContainer.add(cardTwo);
        mainContainer.add(cardContainer, BorderLayout.CENTER);
    }

    private void setupButtons() {
        buttonContainer.setLayout(new GridLayout());
        buttonContainer.add(leftSpacer);
        buttonContainer.add(dislikeButton);
        buttonContainer.add(centerSpacer);
        buttonContainer.add(likeButton);
        buttonContainer.add(rightSpacer);

        likeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCatRemoved();
                cardLayout.next(cardContainer);
                if (catCardInfos.size() > 0) {
                    loadNextCard(catCardInfos.get(0).imageUrl);
                }
            }
        });

        dislikeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCatRemoved();
                cardLayout.next(cardContainer);
                if (catCardInfos.size() > 0) {
                    loadNextCard(catCardInfos.get(0).imageUrl);
                }
            }
        });

        // adds the buttons to the bottom
        mainContainer.add(buttonContainer, BorderLayout.SOUTH);
    }

    private void onCatRemoved() {
        int numCats = catCardInfos.size();
        if (numCats > 0) {
            catCardInfos.remove(0);
        }

        if (numCats < 3) {
            onCatsAboutToEmpty();
        }
    }

    private void onCatsAboutToEmpty() {
        presenter.loadCats();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CatCardsView Implementation
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void addCards(List<CatCardInfo> catCardInfos) {
        this.catCardInfos.addAll(catCardInfos);
        if (!hasShownFirstCat) {
            hasShownFirstCat = true;
            loadNextCard(this.catCardInfos.get(0).imageUrl);
        }
    }

    void loadNextCard(final String path) {
        rx.Observable.create(new Observable.OnSubscribe<Image>() {
            @Override
            public void call(Subscriber<? super Image> subscriber) {
                try {
                    Image image = Toolkit.getDefaultToolkit().getImage(new URL(path));
                    subscriber.onNext(image);
                    subscriber.onCompleted();
                } catch (MalformedURLException e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(image -> {
            populateNextCard(image);
        });
    }

    void populateNextCard(Image image) {
        if (currentCard == cardOne) {
            // load next photo into cardOne
            cardOne.setImage(image);
            currentCard = cardTwo;
        } else {
            // load next photo into cardTwo
            cardTwo.setImage(image);
            currentCard = cardOne;
        }
    }

    // Quick hack to get the UI up and running
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CatSwipeUI frame = new CatSwipeUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //Set up the content pane.
                frame.addComponentsToPane(frame.getContentPane());
                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    static class ImagePanel extends JPanel {

        Image image;

        public void setImage(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (image != null) {
                Insets insets = getInsets();
                g.drawImage(image, insets.left, insets.top, this);
            }
        }
    }
}

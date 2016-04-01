package com.github.cleancattinder.catswiping;

import java.awt.*;

import javax.swing.*;

class CardFlipper extends JPanel {

    private final CardLayout cardLayout = new CardLayout();
    private CardFlipAdapter adapter;

    public CardFlipper() {
        setLayout(cardLayout);
    }

    public void showFirst() {
        adapter.addCard(this)
               .subscribe(aVoid -> {
                   cardLayout.first(CardFlipper.this);
               });

        adapter.addCard(this).subscribe();
    }

    public void showNext() {
        cardLayout.next(CardFlipper.this);
        remove(0);
        adapter.addCard(this).subscribe();
    }

    public void setAdapter(CardFlipAdapter adapter) {
        this.adapter = adapter;
    }
}

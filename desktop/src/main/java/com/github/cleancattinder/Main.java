package com.github.cleancattinder;

import com.github.cleancattinder.catswiping.CatSwipeUI;
import com.github.cleancattinder.rx.DesktopSchedulerFactory;

import javax.swing.*;

public class Main {

    // Quick hack to get the UI up and running
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final DesktopSchedulerFactory schedulerFactory = new DesktopSchedulerFactory();

            CatSwipeUI ui = new CatSwipeUI(false, schedulerFactory);
            ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // Set up the content pane.
            ui.addComponentsToPane(ui.getContentPane());

            // Display the window.
            ui.pack();
            ui.setVisible(true);

            // Load the cats
            ui.load();
        });
    }
}

package com.github.cleancattinder.catswiping;

import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.github.cleancattinder.rx.SchedulerFactory;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

class CardFlipAdapter {
    private List<CatCardInfo> data = new ArrayList<>();
    private final SchedulerFactory schedulerFactory;
    private final CardAdapterListener listener;

    interface CardAdapterListener {
        void onAdapterAboutToEmpty();
    }

    CardFlipAdapter(SchedulerFactory schedulerFactory, CardAdapterListener listener) {
        this.schedulerFactory = schedulerFactory;
        this.listener = listener;
    }

    public void addCards(List<CatCardInfo> data) {
        this.data.addAll(data);
    }

    public Observable<Void> addCard(CardFlipper cardFlipper) {
        final PublishSubject<Void> subject = PublishSubject.create();

        final CatCardInfo info = data.remove(0);

        if (data.size() < 3) {
            listener.onAdapterAboutToEmpty();
        }

        loadImage(cardFlipper, info.imageUrl)
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.mainThread())
            .subscribe(image -> {
                cardFlipper.add(new ImagePanel(image));
                subject.onNext(null);
            });

        return subject;
    }

    private Observable<Image> loadImage(CardFlipper cardFlipper, final String path) {
        return Observable.create(new Observable.OnSubscribe<Image>() {
            @Override
            public void call(Subscriber<? super Image> subscriber) {
                try {
                    Image image = Toolkit.getDefaultToolkit()
                                         .getImage(new URL(path));
                    image = image.getScaledInstance(cardFlipper.getWidth(), -1, Image.SCALE_SMOOTH);
                    subscriber.onNext(image);
                    subscriber.onCompleted();
                }
                catch (MalformedURLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}

package com.github.cleancattinder.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.schedulers.SwingScheduler;

public class DesktopSchedulerFactory implements SchedulerFactory {
    @Override
    public Scheduler mainThread() {
        return SwingScheduler.getInstance();
    }

    @Override
    public Scheduler immediate() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.newThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler test() {
        return Schedulers.test();
    }
}

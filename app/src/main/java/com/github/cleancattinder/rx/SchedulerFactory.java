package com.github.cleancattinder.rx;

import rx.Scheduler;

/**
 * Hint: Decouple your scheduling from your algorithm!
 *
 * "Objects are abstractions of processing. Threads are abstractions of schedule."
 */
public interface SchedulerFactory {

    Scheduler mainThread();

    Scheduler immediate();

    Scheduler trampoline();

    Scheduler newThread();

    Scheduler computation();

    Scheduler io();

    Scheduler test();
}

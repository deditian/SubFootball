package com.dedi.subfootball.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider: SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}
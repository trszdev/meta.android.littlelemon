package com.meta.littlelemon.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B> zip(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>>
        = MediatorLiveData<Pair<A, B>>().apply {
    var lastA: A? = null
    var lastB: B? = null

    fun update() {
        val localLastA = lastA
        val localLastB = lastB
        if (localLastA != null && localLastB != null)
            value = Pair(localLastA, localLastB)
    }

    addSource(a) {
        lastA = it
        update()
    }
    addSource(b) {
        lastB = it
        update()
    }
}

fun <T> distinct(source: LiveData<T>): LiveData<T>
        = MediatorLiveData<T>().apply {
    addSource(source) {
        if (value != it) value = it
    }
}


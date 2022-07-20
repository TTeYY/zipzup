package com.tteyy.zipzup.test.common

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import spock.lang.Shared
import spock.lang.Specification

class ZipzupSpecification extends Specification {

    @Shared
    Continuation coroutineContext = Mock() {
        _ * getContext() >> Mock(CoroutineContext)
    }

    static String getUuid() {
        UUID.randomUUID().toString()
    }

    static List<String> buildUuidList(size) {
        (1..size).collect { uuid }
    }
}

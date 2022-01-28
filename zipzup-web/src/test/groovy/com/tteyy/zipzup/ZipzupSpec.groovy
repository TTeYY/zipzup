package com.tteyy.zipzup

import spock.lang.Specification

class ZipzupSpec extends Specification {

    def "test"() {
        given:
        def a = 3

        when:
        a = a * 3

        then:
        a == 9
    }

}

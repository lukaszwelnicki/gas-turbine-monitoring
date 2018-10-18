package com.software.lukaszwelnicki.msc.utils

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RandomUtilsTest extends Specification {

    def "should return random value by middle = #a and spread = #b"() {
        given:
            double rand = RandomUtils.randomMiddleAndSpread(a, b)

        expect:
            rand >= a - b / 2
            rand <= a + b / 2

        where:
            a  | b
            5  | 2
            7  | 3
            -5 | 3

    }

    def "should throw IllegalArgumentException when spread <= 0"() {
        when:
            RandomUtils.randomMiddleAndSpread(1, spread)

        then:
            IllegalArgumentException ex = thrown()
            ex.getMessage() == "Spread value should be greater than 0."

        where:
            spread << [-1, 0]
    }

}

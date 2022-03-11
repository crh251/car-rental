package laijh.carrental.common

import spock.lang.Specification

class ComonUtilSpec extends Specification {

    def "should test"() {
        given:
        def a = 1
        def b = 2

        when:
        def result = a + b

        then:
        result == 1
    }

}

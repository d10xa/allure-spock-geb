package ru.d10xa.allure

import spock.lang.Specification

public class NotGebSpec extends Specification {
    def 'extension do not break normal Specification'() {
        expect: true
    }
}

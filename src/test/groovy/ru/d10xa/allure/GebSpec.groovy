package ru.d10xa.allure

import geb.spock.GebReportingSpec
import spock.lang.Stepwise

@Stepwise
public class GebSpec extends GebReportingSpec {

    def 'every step ends with report method execution'() {
        println System.getProperty('gradleServerPort')
        def string = "http://localhost:${System.getProperty('gradleServerPort')}/index.html"
        println string
        when:
        go string
        then:
        $("p").text() == 'hello'
    }

    def 'check results directory after first step'() {
        when:
        def resultsDir = new File('build/allure-results').listFiles()

        then:
        resultsDir[0].name.endsWith 'attachment.html'
    }

}

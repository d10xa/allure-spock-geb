package ru.d10xa.allure

import groovy.transform.CompileStatic
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo

@CompileStatic
class GlobalAllureExtension implements IGlobalExtension {

    @Override
    void visitSpec(SpecInfo spec) {
        spec.addSetupSpecInterceptor new AllureSpecInterceptor()
    }

    @Override
    void start() {}

    @Override
    void stop() {}
}

package ru.d10xa.allure

import geb.Browser
import geb.spock.GebSpec
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

class AllureSpecInterceptor extends AbstractMethodInterceptor {

    @Override
    void interceptSetupSpecMethod(IMethodInvocation invocation) {
        def specInstance = invocation.instance
        if (specInstance instanceof GebSpec) {
            def browser = specInstance.browser as Browser
            try {
                Class.forName("io.qameta.allure.spock.AllureSpock")
                browser.config.reportingListener = new Allure2ReportingListener()
            } catch (ClassNotFoundException e){
                browser.config.reportingListener = new AllureReportingListener()
            }
        }
        invocation.proceed()
    }

}

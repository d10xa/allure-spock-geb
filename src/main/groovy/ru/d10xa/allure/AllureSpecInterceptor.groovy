package ru.d10xa.allure

import geb.Browser
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

class AllureSpecInterceptor extends AbstractMethodInterceptor {

    @Override
    void interceptSetupSpecMethod(IMethodInvocation invocation) {
        def browser = invocation.instance.browser as Browser
        browser.config.reportingListener = new AllureReportingListener()
        invocation.proceed()
    }

}

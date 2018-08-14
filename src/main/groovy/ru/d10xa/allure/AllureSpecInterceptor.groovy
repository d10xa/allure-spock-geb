package ru.d10xa.allure

import geb.Browser
import geb.Page
import geb.PageChangeListener
import geb.spock.GebSpec
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

class AllureSpecInterceptor extends AbstractMethodInterceptor {

    @Override
    void interceptSetupSpecMethod(IMethodInvocation invocation) {
        def specInstance = invocation.instance
        if (specInstance instanceof GebSpec) {
            def browser = specInstance.browser as Browser

            browser.driver
//            Capturing browser logs with Selenium
//            http://stackoverflow.com/questions/25431380/capturing-browser-logs-with-selenium
            browser.config.reportingListener = new AllureReportingListener()
            browser.registerPageChangeListener(new PageChangeListener() {
                @Override
                void pageWillChange(Browser b, Page oldPage, Page newPage) {
//                    b.onLoad()
                    b
                    System.err.println("update!")
                    System.err.println(oldPage)
                    System.err.println(newPage)

                    def proxyLogInit = """
        if(!window.logStore){
            window.logStore = {};
            window.logStore.logs = [];
            window.logStore.oldf = console.log;
            console.log = function(){
                window.logStore.logs.push(arguments);
                window.logStore.oldf.apply(console, arguments);
            }
            window.logStore.getAndClean = function(){
                var logs = window.logStore.logs;
                window.logStore.logs = [];
                return logs;
            }
        }
"""
                    b.js.exec(proxyLogInit)
                }
            })
        }
        invocation.proceed()
    }

}

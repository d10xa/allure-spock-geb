package ru.d10xa.allure

import geb.Browser
import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo
import ru.yandex.qatools.allure.Allure
import ru.yandex.qatools.allure.events.MakeAttachmentEvent

class GlobalAllureScreenshotExtension implements IGlobalExtension {

    @Override
    void visitSpec(SpecInfo spec) {
        spec.addSetupSpecInterceptor(new AbstractMethodInterceptor() {
            @Override
            void interceptSetupSpecMethod(IMethodInvocation invocation) {
                def browser = invocation.instance.browser as Browser
                browser.config.reportingListener = new ReportingListener() {
                    @Override
                    void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
                        reportFiles.findAll { it.name.endsWith(".png") }.each { it ->
                            Allure.LIFECYCLE.fire(new MakeAttachmentEvent(it.bytes, it.name, "image/png"))
                        }
                    }
                }
            }
        })
    }

    @Override
    void start() {}

    @Override
    void stop() {}
}

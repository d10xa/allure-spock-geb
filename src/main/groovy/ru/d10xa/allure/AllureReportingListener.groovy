package ru.d10xa.allure

import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import groovy.transform.CompileStatic
import ru.yandex.qatools.allure.Allure
import ru.yandex.qatools.allure.events.MakeAttachmentEvent

@CompileStatic
class AllureReportingListener implements ReportingListener {

    @Override
    void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
        def exec = reportState.browser.js.exec("if(window.logStore) {window.logStore.getAndClean();}");
        def fileGroups = reportFiles.groupBy { it.name.split("\\.")[-1] }
        fileGroups['png']?.each { makeAttachment(it, "image/png") }
        fileGroups['html']?.each { makeAttachment(it, "text/html") }
        Allure.LIFECYCLE.fire(new MakeAttachmentEvent(
               exec.toString().bytes , UUID.randomUUID().toString()+".json", "application/json"))
    }

    private void makeAttachment(File file, String type) {
        Allure.LIFECYCLE.fire(new MakeAttachmentEvent(file.bytes, file.name, type))
    }

}

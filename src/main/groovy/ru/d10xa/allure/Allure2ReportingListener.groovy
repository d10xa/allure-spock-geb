package ru.d10xa.allure

import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import groovy.transform.CompileStatic
import io.qameta.allure.spock.AllureSpock


@CompileStatic
class Allure2ReportingListener extends AllureSpock implements ReportingListener {

    @Override
    void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
        def fileGroups = reportFiles.groupBy { it.name.split("\\.")[-1] }
        fileGroups['png']?.each { makeAttachment(it, "image/png", ".png") }
        fileGroups['html']?.each { makeAttachment(it, "text/html", ".html") }
    }

    private void makeAttachment(File file, String type, String fileExtension) {
        getLifecycle().addAttachment(file.name, type, fileExtension, file.bytes)
    }

}

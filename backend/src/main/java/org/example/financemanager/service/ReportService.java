package org.example.financemanager.service;

import org.example.financemanager.dto.GroupedReportDto;
import org.example.financemanager.dto.RequestDataForReport;
import org.example.financemanager.dto.GeneralReportResponse;

public interface ReportService {
    GroupedReportDto generateGroupedReport(RequestDataForReport requestDataForReport, String username);

    GeneralReportResponse generateTotalReport(RequestDataForReport requestDataForReport, String username);
}

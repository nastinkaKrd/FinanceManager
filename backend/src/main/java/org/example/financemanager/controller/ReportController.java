package org.example.financemanager.controller;

import lombok.AllArgsConstructor;
import org.example.financemanager.dto.GroupedReportDto;
import org.example.financemanager.dto.RequestDataForReport;
import org.example.financemanager.dto.GeneralReportResponse;
import org.example.financemanager.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/grouped")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupedReportDto getGroupedReport(@RequestBody RequestDataForReport requestDataForReport,
                                             @AuthenticationPrincipal UserDetails userDetails){
        return reportService.generateGroupedReport(requestDataForReport, userDetails.getUsername());
    }

    @PostMapping("/general")
    @ResponseStatus(HttpStatus.CREATED)
    public GeneralReportResponse getGeneralReport(@RequestBody RequestDataForReport requestDataForReport,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        return reportService.generateTotalReport(requestDataForReport, userDetails.getUsername());
    }
}

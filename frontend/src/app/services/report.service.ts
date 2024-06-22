import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {GroupedReportDto} from "../common/grouped-report-dto";
import {TotalReportResponse} from "../common/total-report-response";
import {RequestDataForReport} from "../common/request-data-for-report";

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private baseUrl: string = 'http://localhost:8080/api/report';

  constructor(private httpClient: HttpClient) { }

  public getGroupedReport(requestData: RequestDataForReport, headers: HttpHeaders): Observable<GroupedReportDto> {
    const url: string = `${this.baseUrl}/grouped`;
    const options = {
      headers: headers,
      responseType: 'json'
    }
    // @ts-ignore
    return this.httpClient.post<GroupedReportDto>(url, requestData, options)
  }

  public getGeneralReport(requestData: RequestDataForReport, headers: HttpHeaders): Observable<TotalReportResponse> {
    const url: string = `${this.baseUrl}/general`;
    const options = {
      headers: headers,
      responseType: 'json'
    }
    // @ts-ignore
    return this.httpClient.post<TotalReportResponse>(url,  requestData, options)
  }

}

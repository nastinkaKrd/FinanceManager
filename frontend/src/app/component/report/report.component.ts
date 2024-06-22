import {Component} from '@angular/core';
import {HeaderService} from "../../services/header.service";
import {ReportService} from "../../services/report.service";
import {RequestDataForReport} from "../../common/request-data-for-report";
import {TransactionService} from "../../services/transaction.service";
import {FormsModule} from "@angular/forms";
import {CurrencyPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {TotalReportResponse} from "../../common/total-report-response";
import {GroupedReportDto} from "../../common/grouped-report-dto";
import Chart from 'chart.js/auto';
import {AmountWithCurrencyResponse} from "../../common/amount-with-currency-response";

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    KeyValuePipe,
    CurrencyPipe
  ],
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent {
  requestDataForReport: RequestDataForReport ={
    "startDate": "",
    "endDate": "",
    "type": "",
    "currency": ""
  }
  currency: String[] = [];
  transactionTypes: String[] = [];
  reportKeys: String[] = [];
  reportValues: Number [] = [];
  colors: string[] = [];
  showGroupedChart: boolean = false;
  showGeneralChart: boolean = false;
  totalAmount: number = 0;
  reportCurrency: string = "";
  dataWithTotalAmountOfCategory: { [p: string]: AmountWithCurrencyResponse } = {};
  generalReportData: { [p: string]: AmountWithCurrencyResponse } = {};

  constructor(private transactionService: TransactionService,
              private reportService: ReportService,
              private headerService: HeaderService) { }



  public getReportForm(){
    this.reportKeys = [];
    this.reportValues = [];
    window.location.reload();
  }
  public getGeneralReport(){
    this.reportService.getGeneralReport(this.requestDataForReport, this.headerService.getAuthHeader()).subscribe(
      (data: TotalReportResponse) => {
        for (let i=0; i<Object.entries(data.data).length; i++){
          const [key, value] = Object.entries(data.data)[i];
          this.reportKeys[i] = key;
          this.reportValues[i] = value.amount;
        }

        this.generalReportData = data.data;
        this.showGeneralChart = true;
        this.createLineChart();
      }
    );
  }

  public getGroupedReport(){
    this.reportService.getGroupedReport(this.requestDataForReport, this.headerService.getAuthHeader()).subscribe(
      (data: GroupedReportDto) => {
        for (let i=0; i<Object.entries(data.dataWithPercentage).length; i++){
          const [key, value] = Object.entries(data.dataWithPercentage)[i];
          this.reportKeys[i] = key + ` ${value}`.substring(0, 5) + "%";
          this.reportValues[i] = value;
        }
        this.totalAmount = data.amount.amount;
        this.reportCurrency = data.amount.currency;
        this.dataWithTotalAmountOfCategory = data.dataWithTotalAmountOfCategory;
        this.generateColors();
        this.createPieChart();
        this.showGroupedChart=true;
      }
    );
  }

  public getTransactionTypes(){
    this.transactionService.getTransactionTypes()
      .subscribe((data: String[]) => {
        this.transactionTypes = data;
      })
  }

  public getCurrency(){
    this.transactionService.getCurrency()
      .subscribe((data: String[]) => {
        this.currency = data;
      })
  }

  generateColors() {
    for (let i = 0; i < this.reportKeys.length; i++) {
      const r = Math.floor(Math.random() * 256);
      const g = Math.floor(Math.random() * 256);
      const b = Math.floor(Math.random() * 256);
      const color = `rgba(${r}, ${g}, ${b}, 0.6)`; // Random RGBA color with 0.6 opacity
      this.colors.push(color);
    }
  }

  createPieChart(): void {
    const ctx = document.getElementById('pieChart') as HTMLCanvasElement;
    const myPieChart = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: this.reportKeys,
        datasets: [{
          label: 'Pie Chart',
          data: this.reportValues,
          backgroundColor: this.colors,
          borderColor: this.colors,
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem: any) => {
                return `${this.reportKeys[tooltipItem.index]}: ${this.reportValues[tooltipItem.index]}`;
              }
            }
          }
        }
      }
    });
  }

  createLineChart(): void {
    const ctx = document.getElementById('lineChart') as HTMLCanvasElement;
    const myLineChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.reportKeys,
        datasets: [{
          label: 'General report',
          data: this.reportValues,
          borderColor: 'rgba(75, 192, 192, 1)',
          tension: 0.1,
          fill: false,
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            mode: 'index',
            intersect: false,
          }
        },
        scales: {
          x: {
            display: true,
            title: {
              display: true,
              text: 'Date'
            }
          },
          y: {
            display: true,
            title: {
              display: true,
              text: 'Amount'
            }
          }
        }
      }
    });
  }

  ngOnInit() {
    this.getTransactionTypes();
    this.getCurrency();
  }
}

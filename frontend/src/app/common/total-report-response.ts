import {AmountWithCurrencyResponse} from "./amount-with-currency-response";

export class TotalReportResponse {
  public data: { [key: string]: AmountWithCurrencyResponse }

  constructor(data: { [p: string]: AmountWithCurrencyResponse }) {
    this.data = data;
  }
}

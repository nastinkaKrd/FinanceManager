import {AmountWithCurrencyResponse} from "./amount-with-currency-response";

export class GroupedReportDto {
  constructor(amount: AmountWithCurrencyResponse, dataWithTotalAmountOfCategory: {
    [p: string]: AmountWithCurrencyResponse
  }, dataWithPercentage: { [p: string]: number }) {
    this.amount = amount;
    this.dataWithTotalAmountOfCategory = dataWithTotalAmountOfCategory;
    this.dataWithPercentage = dataWithPercentage;
  }
  public amount: AmountWithCurrencyResponse;
  public dataWithTotalAmountOfCategory: { [key: string]: AmountWithCurrencyResponse }
  public dataWithPercentage: { [key: string]: number }
}

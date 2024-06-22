export class RequestDataForReport {
  constructor(startDate: String, endDate: String, type: String, currency: String) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.type = type;
    this.currency = currency;
  }
  public startDate: String;
  public endDate: String;
  public type: String;
  public currency: String;
}

import {Category} from "./category";

export class RequestTransactionData {
  public description: string;
  public date: string;
  public type: string;
  public amount: number;
  public currency: string;
  public category: Category;
  constructor(description: string, date: string, type: string, amount: number, currency: string, category: Category) {
    this.description = description;
    this.date = date;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.category = category;
  }
}

import {CategoryDto} from "./category-dto";

export class Transaction {
  public id: number;
  public description: string;
  public date: string;
  public type: string;
  public amount: number;
  public currency: string;
  public category: CategoryDto;
  constructor(id: number, description: string, date: string, type: string, amount: number, currency: string, category: CategoryDto) {
    this.id = id;
    this.description = description;
    this.date = date;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.category = category;
  }
}

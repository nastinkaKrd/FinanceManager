import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category.service";
import {HeaderService} from "../../services/header.service";
import {TransactionService} from "../../services/transaction.service";
import {RequestTransactionData} from "../../common/request-transaction-data";
import {CategoryDto} from "../../common/category-dto";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-add-transaction',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './add-transaction.component.html',
  styleUrl: './add-transaction.component.css'
})
export class AddTransactionComponent {
  categories: CategoryDto[] = [];
  currency: String[] = [];
  transactionTypes: String[] = [];
  transaction: RequestTransactionData ={
    "description": "",
    "date": "",
    "type": "",
    "amount": 0,
    "currency": "",
    "category": {
      "id": 0,
      "title": "",
      "description": ""
    }
  }

  constructor(private router: Router,
              private transactionService: TransactionService,
              private categoryService: CategoryService,
              private headerService: HeaderService) { }

  public addTransaction(){
    this.categories.forEach((category: CategoryDto) => {
      if (category.title == this.transaction.category.title){
        this.transaction.category.id = category.id;
        this.transaction.category.description = category.description;
        return;
      }
    })
    this.transactionService.addTransaction(this.transaction, this.headerService.getAuthHeader()).subscribe();
    this.router.navigate(['/api/transactions']).then(window.location.reload);
  }

  public getCategories() {
    this.categoryService.getCategories(this.headerService.getAuthHeader())
      .subscribe((data: CategoryDto[]) => {
        this.categories = data;
      });
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
  ngOnInit() {
    this.getCategories();
    this.getTransactionTypes();
    this.getCurrency();
  }
}

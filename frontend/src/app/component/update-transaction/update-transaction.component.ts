import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {CategoryDto} from "../../common/category-dto";
import {RequestTransactionData} from "../../common/request-transaction-data";
import {ActivatedRoute, Router} from "@angular/router";
import {TransactionService} from "../../services/transaction.service";
import {CategoryService} from "../../services/category.service";
import {HeaderService} from "../../services/header.service";

@Component({
  selector: 'app-update-transaction',
  standalone: true,
    imports: [
        FormsModule,
        NgForOf,
        ReactiveFormsModule
    ],
  templateUrl: './update-transaction.component.html',
  styleUrl: './update-transaction.component.css'
})
export class UpdateTransactionComponent {
  id: number = 0;
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

  constructor(private route: ActivatedRoute,
              private router: Router,
              private transactionService: TransactionService,
              private categoryService: CategoryService,
              private headerService: HeaderService) { }

  public changeTransaction(){
    this.transactionService.changeTransaction(this.transaction, this.id, this.headerService.getAuthHeader()).subscribe();
    this.router.navigate(['/api/transactions']);
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

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.transaction.category.title = params['category'];
      this.transaction.description = params['description'];
      this.id = params['id'];
      this.transaction.amount = params['amount'];
      this.transaction.currency = params['currency'];
      this.transaction.type = params['type'];
      this.transaction.date = params['date'];
    });
    this.getCategories();
    this.getTransactionTypes();
    this.getCurrency();
  }
}

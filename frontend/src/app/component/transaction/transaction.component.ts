import {Component} from '@angular/core';
import {HeaderService} from "../../services/header.service";
import {TransactionService} from "../../services/transaction.service";
import {Transaction} from "../../common/transaction";
import {NgForOf, NgIf} from "@angular/common";
import {CategoryService} from "../../services/category.service";
import {CategoryDto} from "../../common/category-dto";
import {Category} from "../../common/category";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css'
})
export class TransactionComponent {
  transactions: Transaction[] = [];
  categories: Category [] = [];
  transactionDescription: String = "";

  constructor(private transactionService: TransactionService,
              private categoryService: CategoryService,
              private headerService: HeaderService) { }

  public getTransactions(){
    this.transactionService.getTransactions(this.headerService.getAuthHeader())
      .subscribe((data: Transaction[]) => {
        this.transactions = data;
      });

  }

  public getCategories(){
    this.categoryService.getCategories(this.headerService.getAuthHeader())
      .subscribe((data: CategoryDto[]) => {
        this.categories = data;
      });

  }

  ngOnInit() {
    this.getTransactions();
    this.getCategories();
  }

  deleteTransaction(id: number) {
    this.transactionService.deleteTransaction(id, this.headerService.getAuthHeader()).subscribe();
    window.location.reload();
  }

  setFilterCategoryValue(categoryId: number){
    this.transactionService.filterByCategory(categoryId, this.headerService.getAuthHeader()).subscribe((data: Transaction[]) => {
      this.transactions = data;
    });
  }

  searchByDescription(){
    this.transactions = [];
    this.transactionService.searchByDescription(this.transactionDescription, this.headerService.getAuthHeader()).subscribe((data: Transaction) => {
      this.transactions[0] = data;
    });
  }
}

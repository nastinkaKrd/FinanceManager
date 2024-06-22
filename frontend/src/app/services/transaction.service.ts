import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Transaction} from "../common/transaction";
import {RequestTransactionData} from "../common/request-transaction-data";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private baseUrl: string = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

  public getTransactions(headers: HttpHeaders): Observable<Transaction[]> {
    const url: string = `${this.baseUrl}/transactions`;
    return this.httpClient.get<Transaction[]>(url, {headers: headers, responseType: 'json'})
  }

  public addTransaction(transaction: RequestTransactionData, headers: HttpHeaders): Observable<void> {
    const url: string = `${this.baseUrl}/transactions`;
    return this.httpClient.post<void>(url, transaction, {headers: headers});
  }

  public changeTransaction(transaction: RequestTransactionData, id: number, headers: HttpHeaders): Observable<void> {
    const url: string = `${this.baseUrl}/transactions/${id}`;
    return this.httpClient.put<void>(url, transaction, {headers: headers});
  }

  public deleteTransaction(id: number, headers: HttpHeaders): Observable<void> {
    const url: string = `${this.baseUrl}/transactions/${id}`;
    return this.httpClient.delete<void>(url, {headers: headers});
  }

  public getTransactionTypes(): Observable<String[]> {
    const url: string = `${this.baseUrl}/transactions/types`;
    return this.httpClient.get<String[]>(url)
  }

  public getCurrency(): Observable<String[]> {
    const url: string = `${this.baseUrl}/transactions/currency`;
    return this.httpClient.get<String[]>(url)
  }

  filterByCategory(categoryId: number, headers: HttpHeaders): Observable<Transaction[]> {
    const url: string = `${this.baseUrl}/transactions/filter?category-id=`+categoryId;
    return this.httpClient.get<Transaction[]>(url, {headers: headers})
  }

  searchByDescription(description: String, headers: HttpHeaders): Observable<Transaction> {
    const url: string = `${this.baseUrl}/transactions/`+description;
    return this.httpClient.get<Transaction>(url, {headers: headers})
  }
}

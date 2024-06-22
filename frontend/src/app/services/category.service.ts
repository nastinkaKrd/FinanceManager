import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CategoryDto} from "../common/category-dto";
import {RequestCategoryData} from "../common/request-category-data";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl: string = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

  public getCategories(headers: HttpHeaders): Observable<CategoryDto[]> {
    const url: string = `${this.baseUrl}/categories`;
    return this.httpClient.get<CategoryDto[]>(url, {headers: headers, responseType: 'json'})
  }

  public addCategory(category: RequestCategoryData, headers: HttpHeaders): Observable<any>  {
    const url: string = `${this.baseUrl}/categories`;
    return this.httpClient.post<any>(url, category, {headers: headers});
  }

  public changeCategory(category: RequestCategoryData, id: number, headers: HttpHeaders): Observable<void> {
    const url: string = `${this.baseUrl}/categories/${id}`;
    return this.httpClient.put<void>(url, category, {headers: headers});
  }

  public deleteCategory(id: number, headers: HttpHeaders): Observable<void> {
    const url: string = `${this.baseUrl}/categories/${id}`;
    return this.httpClient.delete<void>(url, {headers: headers});
  }
}

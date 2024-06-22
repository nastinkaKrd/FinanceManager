import { Injectable } from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  public getAuthHeader(): HttpHeaders {
    let storedToken: string | null = null;
    if (typeof sessionStorage !== 'undefined') {
      storedToken = sessionStorage.getItem('Token');
    }


    return storedToken ? new HttpHeaders({
      'Access-Control-Allow-Methods': '*',
      'Access-Control-Allow-Origin': '*',
      'Authorization': "Bearer " + storedToken}) : new HttpHeaders();
  }
}

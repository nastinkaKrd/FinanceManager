import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthResponse} from "../common/auth-response";
import {LoginRequest} from "../common/loginRequest";
import {RegisterRequest} from "../common/register-request";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseUrl: string = 'http://localhost:8080';
  constructor(private httpClient: HttpClient) { }

  public authenticate(loginRequest: LoginRequest): Observable<AuthResponse> {
    const url: string = `${this.baseUrl}/api/auth/login`;
    return this.httpClient.post<AuthResponse>(url, loginRequest);
  }

  public register(registerRequest: RegisterRequest): Observable<string> {
    const url: string = `${this.baseUrl}/api/auth/signup`;
    return this.httpClient.post<string>(url, registerRequest);
  }

  public setAuthHeader(token: string): void {
    sessionStorage.setItem('Token', token)
  }

  public getUserDetails(headers: HttpHeaders): Observable<any> {
    const url: string = `${this.baseUrl}/api/auth/get-user-details`;
    return this.httpClient.get(url, {headers: headers, responseType: 'text'})
  }

  public confirmEmail(token: string):Observable<AuthResponse>{
    const url: string = `${this.baseUrl}/api/auth/email-confirm/`+token;
    return this.httpClient.get<AuthResponse>(url);
  }

  public logout(): void {
    sessionStorage.removeItem('Token')
  }
}

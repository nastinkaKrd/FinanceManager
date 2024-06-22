import { Component } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {LoginRequest} from "../../common/loginRequest";
import {RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,
    RouterLink, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginData:  LoginRequest ={
    "username": "",
    "password": ""
  }
  constructor(private authenticationService: AuthenticationService) { }

  public authenticate(): void{
    this.authenticationService.authenticate(this.loginData)
      .subscribe(data=>{
        this.setAuthHeader(data.accessToken);
        window.location.reload();
      });
  }

  public setAuthHeader(token: string){
    this.authenticationService.setAuthHeader(token);
  }

}

import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {AuthenticationService} from "../../services/authentication.service";
import {RouterLink} from "@angular/router";
import {RegisterRequest} from "../../common/register-request";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerData: RegisterRequest ={
    "email": "",
    "username": "",
    "password": ""
  }
  constructor(private authenticationService: AuthenticationService) { }

  register(): void{
    this.authenticationService.register(this.registerData).subscribe(data=>{
      console.log(data);
      window.location.reload();
    });
  }
}

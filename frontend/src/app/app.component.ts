import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {HttpClientModule} from "@angular/common/http";
import {AuthenticationService} from "./services/authentication.service";
import {HeaderService} from "./services/header.service";
import {NgIf} from "@angular/common";
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, FormsModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  public isAuthenticated: boolean = false;
  public username: string = "";
  constructor(private authenticationService: AuthenticationService,
              private headerService: HeaderService) {
  }

  ngOnInit() {
    this.isAuthentication();
  }

  isAuthentication(): void {
    this.authenticationService.getUserDetails(this.headerService.getAuthHeader()).subscribe(
      (data: string): void => {
        if (data !== 'null'){
          this.isAuthenticated = true;
          this.username = data;
        }
      }
    );
  }

  public logout(): void{
    this.authenticationService.logout();
  }
}

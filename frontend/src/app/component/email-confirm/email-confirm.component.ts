import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-email-confirm',
  standalone: true,
  imports: [],
  templateUrl: './email-confirm.component.html',
  styleUrl: './email-confirm.component.css'
})
export class EmailConfirmComponent {
  token: string = "";
  constructor(private route: ActivatedRoute,
              private authenticationService: AuthenticationService) { }

  public confirmEmail(){
    this.authenticationService.confirmEmail(this.token).subscribe(data=>{
      this.authenticationService.setAuthHeader(data.accessToken);
      window.location.reload();
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
    });
    this.confirmEmail();
  }

}

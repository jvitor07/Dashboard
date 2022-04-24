import { Component, OnInit } from '@angular/core';
import { credentialsDTO } from 'src/app/core/dtos/credentials';
import { ToastService } from 'src/app/core/services/toast.service';
import { AuthService } from 'src/app/core/services/v1/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  public form: credentialsDTO = {} as credentialsDTO;
  
  constructor(private authService: AuthService, private toastService: ToastService) { }

  ngOnInit(): void {
  }

  public authenticate(): void {
    this.authService
      .authenticate(this.form)
      .subscribe({
        next: this.handleSuccessfulAuthentication.bind(this),
        error: this.handleFailureAuthentication.bind(this)
      });
  }

  private handleSuccessfulAuthentication(res: any) {
    console.log(res);
  }

  private handleFailureAuthentication(err: any) {
    if(err.status !== 500) {
      const messages: Array<string> = err.error.messages;
      messages.forEach(message => this.toastService.showErrorMessage(message));
    } else {
      this.toastService.showErrorMessage('Ocorreu um erro desconhecido. Por favor, tente novamente.');
    }
  }
}

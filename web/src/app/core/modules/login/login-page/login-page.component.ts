import { Component, OnInit } from '@angular/core';
import { credentialsDTO } from 'src/app/core/dtos/credentials';
import { LocalStorageService } from 'src/app/core/services/localStorage.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { AuthService } from 'src/app/core/services/v1/auth.service';
import { ResponseApiDTO } from '../../../dtos/responseApi';
import { AuthResponseDTO } from 'src/app/core/dtos/authResponseDTO';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  public form: credentialsDTO = {} as credentialsDTO;
  
  constructor(private authService: AuthService, private toastService: ToastService, private localStorageService: LocalStorageService, private router: Router) { }

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

  private handleSuccessfulAuthentication(res: ResponseApiDTO<AuthResponseDTO>) {
    this.localStorageService.saveAccessToken(res.responseObject.token);
    this.localStorageService.saveUser(res.responseObject.user);
    this.router.navigateByUrl('/');
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

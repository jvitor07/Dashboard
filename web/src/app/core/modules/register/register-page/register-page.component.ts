import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/models/user';
import { UserService } from 'src/app/core/services/v1/user.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {
  public form: User = {} as User;

  constructor(private userService: UserService, private toastService: ToastService) { }

  ngOnInit(): void {
  }

  public register(): void {
    this.userService.register(this.form)
      .subscribe({
        next: this.handleSuccessfulRegistration.bind(this),
        error: this.handleErrorRegistration.bind(this)
      });
  }

  private handleSuccessfulRegistration(user: User): void {
    console.log(user);
  }

  private handleErrorRegistration(err: any): void {
    if(err.status !== 500) {
      const messages: Array<string> = err.error.messages;
      messages.forEach(message => {
        this.toastService.showErrorMessage(message);
      });
    } else {
      this.toastService.showErrorMessage('Ocorreu um erro desconhecido. Por favor, tente novamente.');
    }
  } 
}

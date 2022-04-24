import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';

@NgModule({
  exports: [RouterModule],
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: LoginPageComponent,
        data: {title: 'Entrar'}
      }
    ])
  ]
})
export class LoginRoutingModule {
}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RegisterPageComponent } from './register-page/register-page.component';

@NgModule({
  exports: [RouterModule],
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: RegisterPageComponent,
        data: {title: 'Cadastrar'}
      }
    ])
  ]
})
export class RegisterRoutingModule {
}

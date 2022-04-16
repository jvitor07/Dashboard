import { NgModule } from '@angular/core';
import { AppComponent } from '../../../app.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { RegisterRoutingModule } from './register.routing.module';

@NgModule({
    declarations: [
        RegisterPageComponent
    ],
    imports: [
        RegisterRoutingModule
    ],
    providers: [
    ],
    bootstrap: [
      AppComponent
    ]
  })  
export class RegisterModule { }
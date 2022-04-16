import { NgModule } from '@angular/core';
import { AppComponent } from '../../../app.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { RegisterRoutingModule } from './register.routing.module';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SharedModule } from '../../../shared/shared.module';

@NgModule({
    declarations: [
        RegisterPageComponent
    ],
    imports: [
        RegisterRoutingModule,
        SharedModule
    ],
    providers: [
    ],
    bootstrap: [
      AppComponent
    ]
  })  
export class RegisterModule { }
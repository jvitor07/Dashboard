import { NgModule } from '@angular/core';
import { AppComponent } from '../../../app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../../shared/shared.module';
import { CommonModule } from '@angular/common';
import { LoginRoutingModule } from './login.routing.module';
import { LoginPageComponent } from './login-page/login-page.component';

@NgModule({
    declarations: [
        LoginPageComponent
    ],
    imports: [
        LoginRoutingModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule, 
        CommonModule
    ],
    providers: [
    ],
    bootstrap: [
      AppComponent
    ]
  })  
export class LoginModule { }
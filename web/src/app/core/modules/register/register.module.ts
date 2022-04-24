import { NgModule } from '@angular/core';
import { AppComponent } from '../../../app.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { RegisterRoutingModule } from './register.routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../../shared/shared.module';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        RegisterPageComponent
    ],
    imports: [
        RegisterRoutingModule,
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
export class RegisterModule { }
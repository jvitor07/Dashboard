import { Component, OnInit } from '@angular/core';
import { credentialsDTO } from 'src/app/core/dtos/credentials';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  public form: credentialsDTO = {} as credentialsDTO;
  
  constructor() { }

  ngOnInit(): void {
  }

}

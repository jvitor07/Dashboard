import { ApiService } from "../api.service";
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from "rxjs";
import { User } from "../../models/user";
import { credentialsDTO } from "../../dtos/credentials";
import { ResponseApiDTO } from "../../dtos/responseApi";
import { IToken } from "../../models/token";

@Injectable({
    providedIn: 'root'
  })
export class AuthService extends ApiService {
    constructor(httpClient: HttpClient) {
        super('/login', httpClient);
    }

    public authenticate(model: credentialsDTO): Observable<ResponseApiDTO<IToken>> {
        return this.post<credentialsDTO, ResponseApiDTO<IToken>>('', model);
    }
}
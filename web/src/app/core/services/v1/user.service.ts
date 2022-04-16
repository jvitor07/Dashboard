import { ApiService } from "../api.service";
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from "rxjs";
import { User } from "../../models/user";

@Injectable({
    providedIn: 'root'
  })
export class UserService extends ApiService {
    constructor(httpClient: HttpClient) {
        super('/api/v1/users', httpClient);
    }

    public register(model: User): Observable<User> {
        return this.post<User, User>('/register', model);
    }
}
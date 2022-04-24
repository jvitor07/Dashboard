import {Injectable} from '@angular/core';
import { User } from '../models/user';

@Injectable({
    providedIn: 'root'
  })
export class LocalStorageService {
    public saveAccessToken(accessToken: string): void {
        localStorage.setItem('access_token', accessToken);
    }

    public saveUser(user: User): void {
        localStorage.setItem('user', JSON.stringify(user));
    }

    public getUser(): User {
        const user = localStorage.getItem('user') ?? '';
        return JSON.parse(user) ?? {} as User;
    }

    public getAccessToken(): string {
        return localStorage.getItem('access_token') ?? '';
    }

    public logout(): void {
        localStorage.removeItem('access_token');
    }
}
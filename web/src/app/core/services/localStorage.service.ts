import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class LocalStorageService {
    public saveAccessToken(accessToken: string): void {
        localStorage.setItem('access_token', accessToken);
    }

    public getAccessToken(): string {
        return localStorage.getItem('access_token') ?? '';
    }

    public logout(): void {
        localStorage.removeItem('access_token');
    }
}
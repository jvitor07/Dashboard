import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

interface IHeader {
    [header: string]: string | string[];
}

interface IParams {
    [key: string]: any;
}

export abstract class ApiService {
    protected readonly baseUrl: string;
    
    protected constructor(endpoint: string, private http: HttpClient) {
        if(endpoint) {
            this.baseUrl = `${environment.API_URL}${endpoint.startsWith('/') ? endpoint : `/${endpoint}`}`;
        } else {
            this.baseUrl = `${environment.API_URL}`
        }
    }

    protected post<T, R>(path: string, object: T, header?: IHeader, params?: IParams): Observable<R> {
        return this.http.post<R>(`${this.baseUrl}${path}`, object, {
            headers: header ?? {} as IHeader,
            params: params ?? {} as IParams
        });
    }
}
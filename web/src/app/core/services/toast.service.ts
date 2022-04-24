import {Injectable} from '@angular/core';
import { ToastConfig, Toaster } from 'ngx-toast-notifications';

@Injectable({
    providedIn: 'root'
  })
export class ToastService {
    constructor(private toast: Toaster) {}

    public showErrorMessage(message: string): void {
        const config = { 
            position: 'top-right',
            type: 'danger',
            preventDuplicates: true
         } as ToastConfig;
         
        this.toast.open(message, config);
    }
}
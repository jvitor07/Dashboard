import {Injectable} from '@angular/core';
import { ToastConfig, Toaster } from 'ngx-toast-notifications';

@Injectable({
    providedIn: 'root'
  })
export class ToastService {
    private config = {
        position: 'top-right',
        preventDuplicates: true
    } as ToastConfig;

    constructor(private toast: Toaster) {}

    public showSuccessMessage(message: string): void {
        this.config.type = 'success';
        this.toast.open(message, this.config);
    }

    public showErrorMessage(message: string): void {
        this.config.type = 'danger';
        this.toast.open(message, this.config);
    }
}
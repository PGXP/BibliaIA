import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '@demoiselle/security';
import { NotificationService } from '../../core/notification.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

  credentials: any = {
    username: '',
    password: '',
    firstName: ''
  };

  constructor(protected authService: AuthService,
    protected router: Router,
    protected notificationService: NotificationService) { }

  ngOnInit() {
    console.log('[RegisterComponent] initialized.');
  }

  register() {
    this.authService.register(this.credentials)
      .subscribe(
      res => {
        this.notificationService.success('Solicitação enviada com sucesso, enviaremos um email para você!');
      },
      error => {
        if (error.status === 401 || error.status === 406) {
          const errors = error.error;
          for (const err of errors) {
            this.notificationService.error(err.error);
          }
          this.credentials.password = '';
        }
      });
  }
}

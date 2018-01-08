import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { SecurityModule } from '@demoiselle/security';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    SecurityModule
  ],
  declarations: [],
  providers: [],
  exports: [
    CommonModule,
    FormsModule,
    RouterModule,
    SecurityModule
  ]
})

export class SharedModule { }

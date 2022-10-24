import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { LoggedInAccountService } from "./logged-in-account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Monkey Movies';
  username: String = '';

  constructor(private router:Router, private _location: Location, private loggedInAccount: LoggedInAccountService) {}

  goToPage(pageName:string):void {
    this.router.navigate([`${pageName}`]);
  }

  goBack(): void {
    this._location.back();
  }

  updateUsername(): String {
    if (!this.loggedInAccount.isLoggedIn()) {
      return '';
    } else {
      return 'Welcome, ' + this.loggedInAccount.getUsername() + '!';
    }
  }
}

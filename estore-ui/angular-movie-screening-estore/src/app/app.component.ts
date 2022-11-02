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
  isUserLoggedIn: boolean = false;

  constructor(private router:Router, private _location: Location, public loggedInAccount: LoggedInAccountService) {
    this.router.navigate(['']);
  }

  goToPage(pageName:string):void {
    this.router.navigate([`${pageName}`]);
  }

  logout(): void {
    this.loggedInAccount.logout();
    this.isUserLoggedIn = false;
    this.router.navigate(['']);
  }

  goBack(): void {
    this._location.back();
  }

  updateUsername(): String {
    if (!this.loggedInAccount.isLoggedIn()) {
      this.isUserLoggedIn = true;
      return '';
    } else {
      this.isUserLoggedIn = false;
      return 'Welcome, ' + this.loggedInAccount.getUsername() + '!';
    }
  }

  voteSuggestMovie(): void {
    if (this.loggedInAccount.isAdmin()) {
      this.router.navigate(['vote-admin']);
    } else {
      this.router.navigate(['vote']);
    }
  }

  inventoryScreen(): void {
    this.router.navigate(['admin']);
  }
}

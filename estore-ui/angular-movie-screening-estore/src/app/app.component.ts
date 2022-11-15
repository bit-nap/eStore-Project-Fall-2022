import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoggedInAccountService } from "./logged-in-account.service";
import { HttpClient } from '@angular/common/http';
import { Accounts } from './Accounts';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Monkey Movies';
  username: String = '';
  isUserLoggedIn: boolean = false;

  constructor(private router:Router, public loggedInAccount: LoggedInAccountService, private http: HttpClient) {
    this.router.navigate(['']);
  }

  goToPage(pageName:string):void {
    this.router.navigate([`${pageName}`]);
  }

  deleteAccount(): void {
    if (confirm("Are you sure you want to delete your account?")) {
      this.http.delete<Accounts>('http://127.0.0.1:8080/accounts/' + this.loggedInAccount.getUsername()).subscribe((data: Accounts) => {
        this.logout();
      });
    }
  }

  logout(): void {
    this.loggedInAccount.logout();
    this.isUserLoggedIn = false;
    this.router.navigate(['']);
  }

  goBack(): void {
    this.router.navigate(['']);
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

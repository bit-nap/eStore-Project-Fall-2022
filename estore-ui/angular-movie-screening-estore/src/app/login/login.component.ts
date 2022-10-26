import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Accounts } from '../Accounts';
import { HttpHeaders } from '@angular/common/http';
import { LoggedInAccountService } from "../logged-in-account.service";
import { Router } from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    Authorization: 'my-auth-token'
  })
};

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  accounts: Accounts[] = [];

  constructor(private http: HttpClient, private loggedInAccount: LoggedInAccountService, private router:Router) { }

  ngOnInit(): void {
  }

  /**
   * Method that will create functionality so the user can create a unique username in the server
   * @param username string of the username passed in from the webpage
   */
  enterUsername(username: string): void {
    this.http.get<[Accounts]>('http://127.0.0.1:8080/accounts/?text='+username).subscribe((data: Accounts[]) => {
      this.accounts = data;
      console.log(this.accounts);
    });

    if (this.accounts.length == 0) {
      this.http.post<Accounts>('http://127.0.0.1:8080/accounts', {"username": username, "password": ""}, httpOptions).subscribe((data: Accounts) => {
        this.accounts[0] = data;
      })
      document.getElementById("newUsernameMessage")!.innerHTML = "Username created";
    }
    else {
      document.getElementById("newUsernameMessage")!.innerHTML = "Username already exists. Please choose another one.";
    }
  }

  /**
   * Sign in the user with the given username.
   * @param username string of the username passed in from the webpage
   */
  signIn(username: String): void {
    this.loggedInAccount.setUsername(username);
    if (username === "admin") {
      this.router.navigate(['admin']);
    }

  }

  /**
   * Method that will create functionality so the user can delete their username
   * @param username string of the username passed in from the webpage
   */
  deleteUsername(username: string) {
    this.http.get<[Accounts]>('http://127.0.0.1:8080/accounts/?text='+username).subscribe((data: Accounts[]) => {
      this.accounts = data;
      console.log(this.accounts);
    });

    if (this.accounts.length == 0) {
      document.getElementById("deleteUsernameMessage")!.innerHTML = "Account does not exist.";
    }
    else {
      this.http.delete<Accounts>('http://127.0.0.1:8080/accounts'+username).subscribe((data: Accounts) => {
        this.accounts[0] = data;
      })
      document.getElementById("deleteUsernameMessage")!.innerHTML = "Account has been deleted.";
    }
  }

}

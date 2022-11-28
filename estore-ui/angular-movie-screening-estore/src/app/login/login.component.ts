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
/**
 * Class to allow the user or admin to log in on the homepage. This will grant certain permissions
 */
export class LoginComponent implements OnInit {
  account: Accounts = {
    id: -1,
    username: "",
    password: ""
  };

  constructor(private http: HttpClient, private loggedInAccount: LoggedInAccountService, private router:Router) { }

  ngOnInit(): void {
  }

  /**
   * Method that will create functionality so the user can create a unique account in the server
   * @param username string of the username passed in from the webpage
   * @param password string of the password passed in from the webpage
   */
  createAccount(username: string, password: string): void {
    if (username === "") {
      this.resetUsernameMessages();
      document.getElementById("newAccountMessage")!.innerHTML = "Please enter a username";
      return;
    } else {
      this.resetUsernameMessages();

      this.http.get<Accounts>('http://127.0.0.1:8080/accounts/'+username).subscribe((data: Accounts) => {
        if (data.username === username) {
          document.getElementById("newAccountMessage")!.innerHTML = "Username already exists. Please choose another one";
        }
      }, (response) => {
        this.http.post<Accounts>('http://127.0.0.1:8080/accounts', {"username": username, "password": password}, httpOptions).subscribe((data: Accounts) => { })
          document.getElementById("newAccountMessage")!.innerHTML = "Username created";
      });
    }
  }

  /**
   * Sign in the user with the given username.
   * @param username string of the username passed in from the webpage
   */
  signIn(username: string, password: string): void {
    if (username === "") {
      this.resetUsernameMessages();
      document.getElementById("loginMessage")!.innerHTML = "Please enter a username";
      return;
    } else {
      this.resetUsernameMessages();
      this.http.get<Accounts>('http://127.0.0.1:8080/accounts/'+username).subscribe((data: Accounts) => {
        if (data.password === password) {
          this.loggedInAccount.setAccount(data);
          this.router.navigate(['']);
        } else if (password === "") {
          document.getElementById("loginMessage")!.innerHTML = "Account requires a password";
        } else {
          document.getElementById("loginMessage")!.innerHTML = "Incorrect password";
        }
      }, (response) => {
        document.getElementById("loginMessage")!.innerHTML = "Username does not exist";
      });
    }
  }

  /**
   * Method that will reset the paragraphs that show messages for the create username, sign in, and delete
   * username methodss
   */
  resetUsernameMessages(): void {
    document.getElementById("newAccountMessage")!.innerHTML = "";
    document.getElementById("loginMessage")!.innerHTML = "";
  }
}

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
   * Method that will create functionality so the user can create a unique username in the server
   * @param username string of the username passed in from the webpage
   */
  enterUsername(username: string): void {
    this.resetUsernameMessages();

    this.http.get<Accounts>('http://127.0.0.1:8080/accounts/'+username).subscribe((data: Accounts) => {
      if (data.username === username) {
        document.getElementById("newUsernameMessage")!.innerHTML = "Username already exists. Please choose another one.";
      }
    }, (response) => {
      this.http.post<Accounts>('http://127.0.0.1:8080/accounts', {"username": username, "password": ""}, httpOptions).subscribe((data: Accounts) => { })
        document.getElementById("newUsernameMessage")!.innerHTML = "Username created";
    });
  }

  /**
   * Sign in the user with the given username.
   * @param username string of the username passed in from the webpage
   */
  signIn(username: String): void {
    this.resetUsernameMessages();

    this.http.get<Accounts>('http://127.0.0.1:8080/accounts/'+username).subscribe((data: Accounts) => {
      this.loggedInAccount.setUsername(username);
      this.loggedInAccount.setId(data.id);
        this.router.navigate(['']);
    }, (response) => {
      document.getElementById("signinUsernameMessage")!.innerHTML = "Username does not exist.";
    });
  }

  /**
   * Method that will create functionality so the user can delete their username
   * @param username string of the username passed in from the webpage
   */
  deleteUsername(username: string): void {
    this.resetUsernameMessages();

    this.http.delete<Accounts>('http://127.0.0.1:8080/accounts/' + username).subscribe((data: Accounts) => {
      document.getElementById("deleteUsernameMessage")!.innerHTML = "Account has been deleted.";
    }, (response) => {
      document.getElementById("deleteUsernameMessage")!.innerHTML = "Account does not exist.";
    });
  }

  /**
   * Method that will reset the paragraphs that show messages for the create username, sign in, and delete
   * username methodss
   */
  resetUsernameMessages(): void {
    document.getElementById("newUsernameMessage")!.innerHTML = "";
    document.getElementById("signinUsernameMessage")!.innerHTML = "";
    document.getElementById("deleteUsernameMessage")!.innerHTML = "";
  }
}

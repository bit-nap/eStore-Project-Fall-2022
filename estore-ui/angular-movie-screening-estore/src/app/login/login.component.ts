import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Accounts } from '../Accounts';
import { HttpHeaders } from '@angular/common/http';

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

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

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

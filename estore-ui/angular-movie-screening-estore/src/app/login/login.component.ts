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
    this.http.get<[Accounts]>('http://127.0.0.1:8080/accounts').subscribe((data: Accounts[]) => {
      this.accounts = data;
      console.log(this.accounts);
    });

    if (this.accounts.length == 0) {
      this.http.post<Accounts>('http://127.0.0.1:8080/accounts', {"username": username, "password": ""}, httpOptions).subscribe((data: Accounts) => {
        this.accounts[0] = data;
      })
      if (document.getElementById("usernameMessage")) {
        document.getElementById("usernameMessage")!.innerHTML = "Username created";
      }
    }
    else {
      if (document.getElementById("usernameMessage")) {
        document.getElementById("usernameMessage")!.innerHTML = "Username already exists. Please choose another one.";
      }
    }
  }

}

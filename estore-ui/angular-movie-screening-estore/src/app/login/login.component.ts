import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Accounts } from '../Accounts'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: Accounts[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  enterUsername(username: string): void {
    this.http.get<[Accounts]>('http://127.0.0.1:8080/accounts').subscribe((data: Accounts[]) => {
      this.username = data;
    })

    if (this.username.length == 0) {
      this.http.post<Accounts>('http://127.0.0.1:8080/accounts', username[0]);
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

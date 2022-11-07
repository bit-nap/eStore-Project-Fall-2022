import { Component, OnInit } from '@angular/core';
// Class interfaces
import { Order } from '../Order';
import { Accounts } from '../Accounts';
// Service
import { LoggedInAccountService } from '../logged-in-account.service';

import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.css']
})
export class PurchaseHistoryComponent implements OnInit {
  // Array of Order objects to store objects from the JSON
  orders: Order[] = [];

  // The account of the user logged in
  user: Accounts = {
    id: -1,
    username: "",
    password: ""
  }

  /**
   * Required objects for deserializing the objects in the JSON based on user logged
   *
   * @param LoginService
   * @param http
   */
  constructor(private login: LoggedInAccountService, private http: HttpClient) { }

  ngOnInit(): void {
    this.getAccount;
    this.getAccountOrders;
  }

  getAccount(): void {
    this.http.get<Accounts>('http://127.0.0.1:8080/accounts/' + this.login.getUsername).subscribe((account_data: Accounts) => {
      this.user = account_data;
    })
  }

  getAccountOrders(): void {
    this.http.get<[Order]>('http://127.0.0.1:8080/orders/?accountId=' + this.user.id).subscribe((orders_data: Order[]) => {
      this.orders = orders_data;
    })
  }
}

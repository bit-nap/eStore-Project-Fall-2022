import { Component, OnInit } from '@angular/core';
// Class interfaces
import { Order } from '../order';
import { Accounts } from '../Accounts';
// Service for logged in user
import { LoggedInAccountService } from '../logged-in-account.service';
// HTTP calls
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
  // Required initialization so initialized to default values
  user: Accounts = {
    id: -1,
    username: "",
    password: ""
  }

  /**
   * Required objects for deserializing the objects in the JSON based on user logged
   *
   * @param login LoggedInAccountService object to keep track of logged in user
   * @param http  HttpClient object for API calls
   */
  constructor(private login: LoggedInAccountService, private http: HttpClient) { }

  ngOnInit(): void {
    this.getAccount();
    // this.getAccountOrders();
  }

  getAccount(): void {
    this.http.get<Accounts>('http://127.0.0.1:8080/accounts/' + this.login.getUsername()).subscribe((account_data: Accounts) => {
      // Check if account exists and has a valid id
      if (account_data != null && account_data.id > 0) {
        // Save user account data
        this.user = account_data;
        document.getElementById("getUsernameMessage")!.innerHTML = "User exists";
      }
    },
    (error) => { document.getElementById("getUsernameMessage")!.innerHTML = "User does not exist"; }
    );
  }

  getAccountOrders(): void {
    this.http.get<[Order]>('http://127.0.0.1:8080/orders/?accountId=' + this.user.id).subscribe((orders_data: Order[]) => {
      if (orders_data != null && orders_data.length > 0) {
        this.orders = orders_data;
        document.getElementById("getOrdersMessage")!.innerHTML = "Displaying user's orders";
      } else {
        document.getElementById("getOrdersMessage")!.innerHTML = "No orders found";
      }
    })
  }
}

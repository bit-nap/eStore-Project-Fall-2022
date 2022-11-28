import { Component, OnInit } from '@angular/core';

// Class interfaces
import { Order } from '../order';

// Service components
import { ScreeningMovieService } from '../screening-movie.service';
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

  /**
   * Required objects for deserializing the objects in the JSON based on user logged
   *
   * @param login LoggedInAccountService object to keep track of logged in user
   * @param http  HttpClient object for API calls
   */
  constructor(private http: HttpClient, private login: LoggedInAccountService, protected SMService: ScreeningMovieService) { }

  ngOnInit(): void {
    this.checkAccount();
    this.getAccountOrders();
  }

  checkAccount(): void {
    if (this.login.getId() === -1)
      document.getElementById("getUsernameMessage")!.innerHTML = "User does not exist";
    else
      document.getElementById("getUsernameMessage")!.innerHTML = "User exists";
  }

  getAccountOrders(): void {
    this.http.get<[Order]>('http://127.0.0.1:8080/orders/?accountId=' + this.login.getId()).subscribe((orders_data: Order[]) => {
      if (orders_data != null && orders_data.length > 0) {
        // Reversing the list of orders to display from most recent to oldest (higher id is more recent)
        this.orders = orders_data.reverse();
        document.getElementById("getOrdersMessage")!.innerHTML = "Displaying " + this.orders.length + " orders";
      } else {
        document.getElementById("getOrdersMessage")!.innerHTML = "No orders found";
      }
    })
  }

  Sodas(order: Order): Boolean {
    if (order.soda.includes(1))
      return true;
    else
      return false;
  }

  Popcorns(order: Order): Boolean {
    if (order.popcorn.includes(1))
      return true;
    else
      return false;
  }
}

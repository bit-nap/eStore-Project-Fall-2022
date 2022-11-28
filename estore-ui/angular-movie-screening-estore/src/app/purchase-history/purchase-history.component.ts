// Angular
import { Component, OnInit } from '@angular/core';

// HTTP calls
import { HttpClient } from '@angular/common/http';

// Class interfaces
import { Order } from '../order';

// Service components
import { ScreeningMovieService } from '../screening-movie.service';
import { LoggedInAccountService } from '../logged-in-account.service';

@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.css']
})
/**
 * Component for a user's movie purchase history. The Orders made on a user's account are obtained
 * and displayed from most recent to past purchases. Each Order represents one Screening. The details
 * of the screening are presented alongside any snacks purchased.
 *
 * @author Group 3C, The Code Monkeys
 */
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

  /**
   * On initialization, check if user account exists and contains orders. If orders are found,
   * Orders array is filled and used to display items on the template.
   */
  ngOnInit(): void {
    this.checkAccount();
    this.getAccountOrders();
  }

  /**
   * Check if the account exist. If user doesn't exist, there are no orders.
   */
  checkAccount(): void {
    if (this.login.getId() === -1)
      document.getElementById("getUsernameMessage")!.innerHTML = "User does not exist";
    else
      document.getElementById("getUsernameMessage")!.innerHTML = "User exists";
  }

  /**
   * HTTP GET command is called to obtain a list of Order objects that are from the user logged in.
   */
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

  /**
   * Check if there are any sodas ordered
   * @param order The Order object that contains sodas array
   * @returns Boolean value, true or false, whether sodas exist
   */
  Sodas(order: Order): Boolean {
    if (order.soda.includes(1))
      return true;
    else
      return false;
  }

  /**
   * Check if there are any popcorns ordered
   * @param order The Order object that contains popcorns array
   * @returns Boolean value, true or false, whether popcorns exist
   */
  Popcorns(order: Order): Boolean {
    if (order.popcorn.includes(1))
      return true;
    else
      return false;
  }
}

import { Component, OnInit } from '@angular/core';
import { LoggedInAccountService } from '../logged-in-account.service';
import { Order } from '../Order';

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
   * @param LoginService
   * @param http
   */
  constructor(private LoginService: LoggedInAccountService, private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<[Order]>('http://127.0.0.1:8080/orders').subscribe((orders: Order[]) => {
      this.orders = orders;
    })
  }


}

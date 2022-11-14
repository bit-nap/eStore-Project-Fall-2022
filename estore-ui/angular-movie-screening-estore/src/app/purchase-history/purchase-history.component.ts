import { Component, OnInit } from '@angular/core';
import { ApplicationRef } from '@angular/core';

// Class interfaces
import { Order } from '../order';
import { Screening } from '../screening';

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

  // Array of Screening objects for screening details
  screenings: Screening[] = [];

  /**
   * Required objects for deserializing the objects in the JSON based on user logged
   *
   * @param login LoggedInAccountService object to keep track of logged in user
   * @param http  HttpClient object for API calls
   */
  constructor(private http: HttpClient, private login: LoggedInAccountService, private appRef: ApplicationRef) { }

  ngOnInit(): void {
    this.checkAccount();
    this.getAccountOrders();
    // this.setArrays();
    // this.printArrays();
  }

  checkAccount(): void {
    if (this.login.getId() === -1)
      document.getElementById("getUsernameMessage")!.innerHTML = "User does not exist";
    else
      document.getElementById("getUsernameMessage")!.innerHTML = "User exists";
  }

  getAccountOrders(): void {
    var that = this;
    this.http.get<[Order]>('http://127.0.0.1:8080/orders/?accountId=' + this.login.getId()).subscribe((orders_data: Order[]) => {
      if (orders_data != null && orders_data.length > 0) {
        // Reversing the list of orders to display from most recent to oldest (higher id is more recent)
        that.orders = orders_data.reverse();
        that.getScreeningsFromOrders(orders_data.reverse());
        document.getElementById("getOrdersMessage")!.innerHTML = "Displaying " + that.orders.length + " orders";
      } else {
        document.getElementById("getOrdersMessage")!.innerHTML = "No orders found";
      }
      this.appRef.tick();
    })
  }

  getScreeningsFromOrders(orderArray: Order[]): void {
    console.log("TEST");
    console.log(orderArray);
    for (var i = 0; i < orderArray.length; i++) {
      var that = this;
      this.http.get<Screening>('http://localhost:8080/screenings/' + orderArray[i].screeningId).subscribe((screening_data: Screening) => {
        // console.log(screening_data);
        that.screenings[i] = screening_data;
        console.log(this.screenings[i]);
        this.appRef.tick();
      })
    }
    console.log("Screenings array");
    console.log(this.screenings);
    console.log("loop finished");
  }

  // setArrays(): void {
  //   var i: number = 0;
  //   for (var order of this.orders) {
  //     this.http.get<Screening>('http://127.0.0.1:8080/screenings/' + order.screeningId).subscribe((screening_data: Screening) => {
  //       this.screenings[i] = screening_data;
  //       console.log("length of screenings array: " + this.screenings.length);
  //       this.http.get<Movie>('http://127.0.0.1:8080/movies/' + screening_data.movieId).subscribe((movie_data: Movie) => {
  //         this.movies[i] = movie_data;
  //         console.log("length of movies array: " + this.movies.length);
  //       })
  //     })
  //     i++;
  //   }
  // }

  // printArrays(): void {
  //   var i: number = 0;
  //   for (var order of this.orders) {
  //     console.log(i + ": " + this.orders[i].id);
  //     console.log(i + ": " + this.screenings[i].id);
  //     console.log(i + ": " + this.movies[i].id);
  //     document.getElementById("getOrdersMessage")!.innerHTML = i + ": " + this.orders[i].id;
  //     document.getElementById("getOrdersMessage")!.innerHTML = i + ": " + this.screenings[i].id;
  //     document.getElementById("getOrdersMessage")!.innerHTML = i + ": " + this.movies[i].id;
  //     i++;
  //   }
  // }

  // setScreeningMovie(screeningId: number): void {
  //   this.http.get<Screening>('http://localhost:8080/screenings/' + screeningId).subscribe((screening_data: Screening) => {
  //     this.screening = screening_data;
  //     this.setMovie(screening_data.movieId);
  //   })
  // }

  // setMovie(movieId: number): void {
  //   this.http.get<Movie>('http://localhost:8080/movies/' + movieId).subscribe((movie_data: Movie) => {
  //     this.movie = movie_data;
  //   })
  // }

  // getMovies(): void {
  //   this.http.get<[Movie]>('http://localhost:8080/movies').subscribe((movie_data: [Movie]) => {
  //     this.movies = movie_data;
  //   })
  // }

  // setScreening(screeningId: number): void {
  //   this.http.get<Screening>('http://localhost:8080/screenings/' + screeningId).subscribe((screening_data: Screening) => {
  //     this.screening = screening_data;
  //   })
  // }

  // getScreeningId(): number {
  //   return <number> this.screening?.id;
  // }

}

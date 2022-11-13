import { Component, OnInit } from '@angular/core';

// Class interfaces
import { Order } from '../order';
import { Screening } from '../screening';
import { Movie } from '../movie';

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

  movie!: Movie;
  screening!: Screening;

  /**
   * Required objects for deserializing the objects in the JSON based on user logged
   *
   * @param login LoggedInAccountService object to keep track of logged in user
   * @param http  HttpClient object for API calls
   */
  constructor(private login: LoggedInAccountService, private http: HttpClient) { }

  ngOnInit(): void {
    this.checkAccount();
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
        document.getElementById("getOrdersMessage")!.innerHTML = "Displaying user's orders";
      } else {
        document.getElementById("getOrdersMessage")!.innerHTML = "No orders found";
      }
    })
  }

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

  // getScreenings(): void {
  //   this.http.get<[Screening]>('http://localhost:8080/screenings').subscribe((screening_data: [Screening]) => {
  //     this.screenings = screening_data;
  //   })
  // }

  // getMovies(): void {
  //   this.http.get<[Movie]>('http://localhost:8080/movies').subscribe((movie_data: [Movie]) => {
  //     this.movies = movie_data;
  //   })
  // }


}

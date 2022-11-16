import { Component, Input, OnInit } from "@angular/core";
import { MoviesComponent } from "../movies/movies.component";
import { MovieSelectorService } from "../movie-selector.service";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { Order } from "../order";
import { ScreeningSelectorService } from "../screening-selector.service";
import { LoggedInAccountService } from "../logged-in-account.service";

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css'],
  providers: [MoviesComponent]
})
export class TicketsComponent implements OnInit {
  /** The number of tickets selected. */
  numOfTickets: number = 0;

  /* URL for the orders */
  private orderUrl: string;

  /** Small, medium and large vlues for beverage (drinks) */
  bsmall_value = 0
  bmedium_value = 0
  blarge_value = 0

  /** Small, medium and large values for popcorn */
  psmall_value = 0
  pmedium_value = 0
  plarge_value = 0

  isModalOpen: boolean = false

  /** the seats and their availability*/
  selectSeats: boolean[][] = this.screeningSelector.getScreeningSeats(); // this is for storage
  selectSeatsCopy: boolean[][] = this.screeningSelector.getScreeningSeats(); //this is for the user to use

  /**
   * Contains the URL for the orders (orderURL)
   *
   * @param router Router that redirects to other pages
   * @param movieSelector Gets the information from current movie
   * @param http HttpClient Gets the link for the orders
   * @param screeningSelector ScreeningSelectorService Gets the information from current screening
   */
  constructor(private router: Router, private movieSelector: MovieSelectorService, private http: HttpClient, private screeningSelector: ScreeningSelectorService, private login: LoggedInAccountService) {
    this.orderUrl = 'http://localhost:8080/orders'
  }

  /**
   * Set this TicketsComponent's selected movie using the MovieSelectorService.
   */
  ngOnInit(): void {
  }

  /**
   * Get the title of the selected movie.
   */
  getMovieTitle(): String {
    return this.movieSelector.getMovieTitle();
  }

  /**
   * Get the poster fo the selected movie.
   */
  getMoviePoster(): String {
    return this.movieSelector.getMoviePoster();
  }

  /**
   * Method called when the user presses the `complete purchase` button.
   *
   * ? Is the number of tickets higher than 0?
   * *  Saves the order in the JSON
   * *  Redirects to `thank` page
   * ? If not?
   * *  Shows error message
   */
  completePurchase(): void {
    // save ticket information as Order class in Java or something
    if (this.numOfTickets > 0) {
      this.saveOrder({ id: 1, screeningId: this.screeningSelector.getScreeningId(), accountId: this.login.getId(), tickets: this.numOfTickets, popcorn: [this.psmall_value, this.pmedium_value, this.plarge_value], soda: [this.bsmall_value, this.bmedium_value, this.blarge_value] })
      this.router.navigate(['thank'])
    } else {
      document.getElementById('error-message')!.innerText = "There's no ticket selected"
    }
  }

  /**
   * Creates an order
   *
   * @param order The order object
   * @returns the order using `orderURL` along with `POST` and the information contained in the object `order`
   */
  public saveOrder(order: Order) {
    return this.http.post<Order>(this.orderUrl, order).subscribe()
  }

  clearMessage(): void {

  }

  /**
   * Functions to add or substract number of drinks
   */
  baddSmall(): void {
    this.bsmall_value++;
  }
  bsubstractSmall(): void {
    if (this.bsmall_value > 0) {
      this.bsmall_value--;
    }
  }
  baddMedium(): void {
    this.bmedium_value++;
  }
  bsubstractMedium(): void {
    if (this.bmedium_value > 0) {
      this.bmedium_value--;
    }
  }
  baddLarge(): void {
    this.blarge_value++;
  }
  bsubstractLarge(): void {
    if (this.blarge_value > 0) {
      this.blarge_value--;
    }
  }

  /**
   * Functions to add or substract number of snacks
   */
  paddSmall(): void {
    this.psmall_value++;
  }
  psubstractSmall(): void {
    if (this.psmall_value > 0) {
      this.bsmall_value--;
    }
  }
  paddMedium(): void {
    this.pmedium_value++;
  }
  psubstractMedium(): void {
    if (this.pmedium_value > 0) {
      this.pmedium_value--;
    }
  }
  paddLarge(): void {
    this.plarge_value++;
  }
  psubstractLarge(): void {
    if (this.plarge_value > 0) {
      this.plarge_value--;
    }
  }

  cancelOrder(): void {
    this.psmall_value = 0;
    this.pmedium_value = 0;
    this.plarge_value = 0;
    this.bsmall_value = 0;
    this.bmedium_value = 0;
    this.blarge_value = 0;
  }

  clearErrorMessage(): void {
    document.getElementById("error-message")!.innerText = ""
  }

  seatCount(row: number, col: number): void {
    if (this.selectSeats[row][col] == false || this.selectSeatsCopy[row][col] == false) {
      this.selectSeatsCopy[row][col] = true;
      this.numOfTickets += 1;
    } else if (this.selectSeats[row][col] == false && this.selectSeatsCopy[row][col] != false) {
      this.selectSeatsCopy[row][col] = false;
      this.numOfTickets -= 1;
    }
    var id: string = row.toString() + col.toString();
    this.changeClass(id);
  }

  emptySeat(row: number, col: number): boolean {
    if (this.selectSeats[row][col] == false) {
      return true;
    }
    return false;
  }

  changeClass(id: string): void {
    var button = document.getElementById(id);
    if (button?.className == 'seat') {
      button.className = 'seat-selected';
    } else if (button?.className == 'seat-selected') {
      button.className = 'seat-selected'
    }
  }
}

import { Component, Input, OnInit } from "@angular/core";
import { MoviesComponent } from "../movies/movies.component";
import { MovieSelectorService } from "../movie-selector.service";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { Order } from "../order";

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css'],
  providers: [MoviesComponent]
})
export class TicketsComponent implements OnInit {
  /** The number of tickets selected. */
  @Input() numOfTickets: Number = 0;
  
  private orderUrl: string;

  bsmall_value = 0
  bmedium_value = 0
  blarge_value = 0
  
  psmall_value = 0
  pmedium_value = 0
  plarge_value = 0

  constructor(private router: Router, private movieSelector: MovieSelectorService, private http: HttpClient) {
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
   * Method to call user presses the complete purchase button.
   */
  completePurchase(): void {
    // TODO: Add actions for completed a purchase, ie, save ticket information as Order class in Java or something
    this.saveOrder({id: 1, screeningId: 5, accountId: 3, tickets: 5, popcorn: [this.psmall_value, this.pmedium_value, this.plarge_value], soda: [this.bsmall_value, this.bmedium_value, this.blarge_value]})
    this.router.navigate(['thank'])
  }

  public saveOrder(order: Order) {
    return this.http.post<Order>(this.orderUrl, order)
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

}

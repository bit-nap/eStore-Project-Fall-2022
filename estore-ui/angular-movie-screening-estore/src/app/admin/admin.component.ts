import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Movie } from '../movie';
import { Screening } from '../screening';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
/**
 * Class for the admin page. From here, the admin will be able to change inventory through adding,
 * changing, and deleteing screenings.
 */
export class AdminComponent implements OnInit {
  newScreening: Screening = {
    id: 0,
    movieId: 0,
    ticketsRemaining: 0,
    date: '',
    time: '',
    seats: []
  };
  screenings: Screening[] = [];
  screeningSelected: boolean = false;
  screeningToChange: Screening = {
    id: 0,
    movieId: 0,
    ticketsRemaining: 0,
    date: '',
    time: '',
    seats: []
  };
  movieForSelectedScreening: Movie = {
    id: 0,
    title: '',
    poster: '',
    runtime: '',
    mpaRating: '',
    year: 0
  };

  /** Constuctor for the admin page to use an http client */
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  /**
   * Method to enter a new screening into the systen
   * @param screeningTicket the ticket for the screening
   * @param screeningDate the date of the screening
   * @param screeningTime the time of the screeening
   * @param screeningMovie the movie associated to the screening
   */
  enterNewScreening(screeningTicket:string, screeningDate:string, screeningTime:string, screeningMovie:string) {
    if (Number(screeningTime) > 20) {screeningTime=="20"};
    this.http.post<Screening>('http://127.0.0.1:8080/screenings', { id: 1, movieId: screeningMovie, ticketsRemaining: screeningTicket.replace(/\D/g, ''), date: screeningDate, time: screeningTime }).subscribe((data:Screening) => {
      this.newScreening = data;
    });
  }

  /**
   * Method that will get the screening selected on the webpage
   * @param screening the screening that will be searched for and selected on the website
   */
  selectScreening(screening: string): void {
    this.screeningSelected = false;
    this.screeningToChange = {
      id: 0,
      movieId: 0,
      ticketsRemaining: 0,
      date: '',
      time: '',
      seats: []
    };
    this.getScreeningListings(screening);
  }

  /**
   * This method will store the screening for the admin to change when it is selected
   * @param screening screening that will be changed
   */
  changeButton(screening: Screening): void {
    this.screeningSelected = true;
    this.screeningToChange = screening;

    this.http.get<Movie>('http://127.0.0.1:8080/movies/'+this.screeningToChange.movieId).subscribe((data: Movie) => {
      this.movieForSelectedScreening = data;
    });
  }

  /**
   * Method that will allow the admin to change a screening. If a certain field was not entered in by the admin,
   * then it will not change. It will only update when the admin puts in a field
   * @param updateTickets the number of tickets to change to
   * @param updateDate the date to change to
   * @param updateTime the time to change to
   */
  changeAccount(updateTickets: string, updateDate: string, updateTime: string): void {
    var tickets = new String("");
    var date = new String("");
    var time = new String("");

    if (updateTickets === null || updateTickets === "") {
      tickets = this.screeningToChange.ticketsRemaining.toString();
    } else {
      tickets = updateTickets;
    }
    if (updateDate === null || updateDate === "") {
      date = this.screeningToChange.date.toString();
    } else {
      date = updateDate;
    }
    if (updateTime === null || updateTime === "") {
      time = this.screeningToChange.time.toString();
    } else {
      time = updateTime;
    }

    this.http.put<Screening>('http://127.0.0.1:8080/screenings', {id: this.screeningToChange.id, movieId: this.screeningToChange.movieId, ticketsRemaining: Number(tickets), date: date, time: time}).subscribe((data: Screening) => {
      this.screeningToChange = data;
    });

    this.getScreeningListings(this.screeningToChange.movieId.toString());
  }

  /**
   * Method that will allow the admin to delete the screening selected
   */
  deleteScreening(): void {
    this.http.delete<[Screening]>('http://127.0.0.1:8080/screenings/'+this.screeningToChange.id).subscribe((data: Screening[]) => {
      this.getScreeningListings(this.screeningToChange.movieId.toString());
    });

    //this.getScreeningListings(this.screeningToChange.movieId.toString());
  }

  /**
   * Method that will get the list of screennigs that share the movie id given
   * @param movieId
   */
  getScreeningListings(movieId: string): void {
    this.http.get<[Screening]>('http://127.0.0.1:8080/screenings/?movieId='+movieId).subscribe((data: Screening[]) => {
      this.screenings = data;
    });
  }

}

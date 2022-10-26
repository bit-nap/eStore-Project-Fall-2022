import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Screenings } from '../Screenings';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  screenings: Screenings[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  enterNewScreening(screeningTicket:string, screeningDate:string, screeningTime:string, screeningMovie:string) {
    this.http.post<Screenings>('http://127.0.0.1:8080/screenings', { id: 1, movieId: screeningMovie, tickets: screeningTicket.replace(/\D/g, ''), date: screeningDate, time: screeningTime }).subscribe((data:Screenings) => {
      this.screenings[0] = data;
    })
  }

}

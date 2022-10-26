import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Screenings } from '../Screenings';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  newScreenings: Screenings[] = [];
  screenings: Screenings[] = [];

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    // Change this to search the screenings by movie id
    /*this.http.get<[Screenings]>('http://127.0.0.1:8080/screenings').subscribe((data: Screenings[]) => {
      this.screenings = data;
    })*/
  }

  enterNewScreening(screeningTicket:string, screeningDate:string, screeningTime:string, screeningMovie:string) {
    this.http.post<Screenings>('http://127.0.0.1:8080/screenings', { id: 1, movieId: screeningMovie, tickets: screeningTicket.replace(/\D/g, ''), date: screeningDate, time: screeningTime }).subscribe((data:Screenings) => {
      this.newScreenings[0] = data;
    })
  }

  selectScreening(screening: string): void {
    this.http.get<[Screenings]>('http://127.0.0.1:8080/screenings/?movieId='+screening).subscribe((data: Screenings[]) => {
      this.screenings = data;
    })
  }

  changeButton(screening: Screenings): void {
    
  }

}

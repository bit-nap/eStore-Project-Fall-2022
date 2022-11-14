import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Suggest } from '../suggest';

@Component({
  selector: 'app-vote-suggest',
  templateUrl: './vote-suggest.component.html',
  styleUrls: ['./vote-suggest.component.css']
})
/**
 * Class for the user to vote and suggest a new movie
 */
export class SuggestComponent implements OnInit {
  suggests: Suggest[] = [];
  newSuggest: Suggest = {
    id: 0,
    movieTitle: '',
    votes: 0
  };

  /** Constructor for the user vote/suggest class to use an http client */
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    document.getElementById("hasUserVoted")!.innerHTML = "";
    this.getSuggestions();
  }

  /**
   * Method for the user to add a vote to a vote object
   * @param vote the vote object that will have a vote added
   */
  addSuggest(vote: Suggest): void {
    this.http.put<Suggest>('http://127.0.0.1:8080/suggestions/', {id: vote.id, movieTitle: vote.movieTitle, votes: (vote.votes+1)}).subscribe((data: Suggest) => {
      this.newSuggest = data;
      this.getSuggestions();
    });

    document.getElementById("hasUserVoted")!.innerHTML = "Thank you for voting!";
  }

  /**
   * Method to all a user to enter a new vote object if it has not already been added
   * @param name the name that will a vote will be made from
   */
  enterSuggest(name: String): void {
    this.http.post<Suggest>('http://127.0.0.1:8080/suggestions/', {id: 1, movieTitle: name, votes: 1}).subscribe((data: Suggest) => {
      this.newSuggest = data;
      this.getSuggestions();
      document.getElementById("hasUserSuggested")!.innerHTML = "Thank you for the suggestion!";
    }, (response) => {
      document.getElementById("hasUserSuggested")!.innerHTML = "Movie has already been suggested.";
    })
  }

  getSuggestions(): void {
    this.http.get<[Suggest]>('http://127.0.0.1:8080/suggestions/').subscribe((data: Suggest[]) => {
      this.suggests = data;
    });
  }
}

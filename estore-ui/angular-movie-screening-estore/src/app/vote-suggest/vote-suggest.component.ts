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
    movieName: '',
    howManyVotes: 0
  };

  /** Constructor for the user vote/suggest class to use an http client */
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    document.getElementById("hasUserVoted")!.innerHTML = "";
    this.http.get<[Suggest]>('http://127.0.0.1:8080/suggestion/').subscribe((data: [Suggest]) => {
      this.suggests = data;
    });
  }

  /**
   * Method for the user to add a vote to a vote object
   * @param vote the vote object that will have a vote added
   */
  addSuggest(vote: Suggest): void {
    this.http.put<Suggest>('http://127.0.0.1:8080/suggestion/', {id: vote.id, movieName: vote.movieName, howManyVotes: (vote.howManyVotes+1)}).subscribe((data: Suggest) => {
      this.newSuggest = data;
    });

    document.getElementById("hasUserVoted")!.innerHTML = "Thank you for voting!";
  }

  /**
   * Method to all a user to enter a new vote object if it has not already been added
   * @param name the name that will a vote will be made from
   */
  enterSuggest(name: String): void {
    this.http.post<Suggest>('http://127.0.0.1:8080/suggestion/', {id: 1, movieName: name, howManyVotes: 1}).subscribe((data: Suggest) => {
      this.newSuggest = data;
      document.getElementById("hasUserSuggested")!.innerHTML = "Thank you for the suggestion!";
    }, (response) => {
      document.getElementById("hasUserSuggested")!.innerHTML = "Movie has already been suggested.";
    })
  }
}

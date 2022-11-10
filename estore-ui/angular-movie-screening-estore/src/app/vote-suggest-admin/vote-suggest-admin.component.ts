import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Suggest } from '../suggest';

@Component({
  selector: 'app-vote-suggest-admin',
  templateUrl: './vote-suggest-admin.component.html',
  styleUrls: ['./vote-suggest-admin.component.css']
})
/**
 * Class to allow the admin to change the vote objects in the system through adding, changing, and deleting them
 */
export class SuggestAdminComponent implements OnInit {
  suggests: Suggest[] = [];
  newSuggest: Suggest = {
    id: 0,
    movieName: '',
    howManyVotes: 0
  };
  suggestSelected: boolean = false;
  suggestSelectedToChange: Suggest = {
    id: 0,
    movieName: "",
    howManyVotes: 0
  };

  /** Constructor for the suggest admin page to allow it to use an http client */
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<[Suggest]>('http://127.0.0.1:8080/suggestions/').subscribe((data: [Suggest]) => {
      this.suggests = data;
    }, (response) => {
      console.log("here");
    })
  }

  /**
   * Method for the admin to add a new suggest to the system
   * @param name name of the movie to be voted on
   */
  enterNewMovie(name: String): void {
    this.http.post<Suggest>('http://127.0.0.1:8080/suggestions/', {id: 1, movieName: name, howManyVotes: 0}).subscribe((data: Suggest) => {
      this.newSuggest = data;
    }, (response) => {
      document.getElementById("adminAddSuggest")!.innerHTML = "Movie has already been added.";
    })
  }

  /**
   * Method to allow the admin to select a movie to change
   * @param vote the vote object selected
   */
  changeSuggest(vote: Suggest): void {
    this.suggestSelected = true;
    this.suggestSelectedToChange = vote;
  }

  /**
   * The method for the admin to change the vote by changing the name
   * @param updateName the name to change
   */
  changeSuggestSubmit(updateName: String): void {
    var name = new String("");

    if (updateName === null || updateName === "") {
      name = this.suggestSelectedToChange.movieName;
    } else {
      name = updateName;
    }

    this.http.put<Suggest>('http://127.0.0.1:8080/suggestions/', {id: 1, movieName: name, howManyVotes: this.suggestSelectedToChange.howManyVotes}).subscribe((data: Suggest) => {
      this.suggestSelectedToChange = data;
    });
  }
}

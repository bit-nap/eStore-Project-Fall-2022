import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';

import { Sodas } from '../Sodas'

@Component({
  selector: 'app-sodas',
  templateUrl: './sodas.component.html',
  styleUrls: ['./sodas.component.css']
})
export class SodasComponent implements OnInit {
  sodas: Sodas[] = [];
  userInventory: Sodas[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<[Sodas]>('http://127.0.0.1:8080/sodas').subscribe(data => {
      this.sodas = data;
    })
  }

  /**
   * Adds a soda to the soda cart
   * @param soda Sodas
   */
  clickSmall(soda: Sodas) {
    this.userInventory.push(soda)
    console.log(this.userInventory)
  }

  /**
   * Removes a soda from the soda cart if the remove button is pressed
   * @param soda Sodas
   */
  removeFromInventory(soda: Sodas) {
    const index = this.userInventory.indexOf(soda)
    this.userInventory.splice(index, 1)
    console.log(this.userInventory);
  }

  /**
   * Empty the soda cart if the customer presses the button cancel
   */
  cancel(): void {
    this.userInventory = []
    console.log(this.userInventory);
  }

  /**
   * TO-DO
   * Confirmes the order of the sodas
   */
  confirm(): void {
    console.log("confirmed");
  }
}

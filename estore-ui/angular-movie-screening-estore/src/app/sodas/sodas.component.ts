import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sodas',
  templateUrl: './sodas.component.html',
  styleUrls: ['./sodas.component.css']
})
export class SodasComponent implements OnInit {

  small_value = 0
  medium_value = 0
  large_value = 0

  constructor() { }

  ngOnInit(): void {
    
  }

  /**
   * Functions to add or substract number of drinks
   */
  addSmall(): void {
    this.small_value++;
  }
  substractSmall(): void {
    if (this.small_value > 0) {
      this.small_value--;
    }
  }
  addMedium(): void {
    this.medium_value++;
  }
  substractMedium(): void {
    if (this.medium_value > 0) {
      this.medium_value--;
    }
  }
  addLarge(): void {
    this.large_value++;
  }
  substractLarge(): void {
    if (this.large_value > 0) {
      this.large_value--;
    }
  }

  /**
   * Resets the value to 0 if the customer presses the button cancel
   */
  cancel(): void {
    this.small_value = 0;
    this.medium_value = 0;
    this.large_value = 0;
  }

  confirm(): void {
    console.log("confirmed");
    
  }
}

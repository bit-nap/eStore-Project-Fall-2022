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
  @Input() selectedSoda?: Sodas;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<[Sodas]>('http://127.0.0.1:8080/sodas').subscribe(data => {
      this.sodas = data;
    })
  }

  onSelect(soda: Sodas): void {
    this.selectedSoda = soda;
  }

  /**
   * Resets the value to 0 if the customer presses the button cancel
   */
  cancel(): void {
  }

  confirm(): void {
    console.log("confirmed");
  }
}

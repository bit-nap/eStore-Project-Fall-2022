import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MoviesComponent } from './movies/movies.component';

// Http component added for angular so that we can make cURL requests from the server
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule //Http module to allow us to make the cURL calls
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

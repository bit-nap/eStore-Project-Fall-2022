import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SodasComponent } from './sodas/sodas.component';
import { LoginComponent } from './login/login.component';
import { MoviesComponent } from './movies/movies.component';
import { TicketsComponent } from "./tickets/tickets.component";
import { CompletedPurchaseComponent } from "./completed-purchase/completed-purchase.component";

const routes: Routes = [
  { path: '', component: MoviesComponent},
  { path: 'sodas', component: SodasComponent },
  { path: 'login', component: LoginComponent },
  { path: 'tickets', component: TicketsComponent},
  { path: 'thank', component: CompletedPurchaseComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SodasComponent } from './sodas/sodas.component';

const routes: Routes = [
  { path: 'sodas', component: SodasComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

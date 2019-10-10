import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashBoardComponent} from './dash-board/dash-board.component';
import {AboutComponent} from './about/about.component';
import {UserHomeComponent} from './user-home/user-home.component';
import {BindingExamplesComponent} from './binding-examples/binding-examples.component';
import {InputRootComponent} from '../inputs/input-root/input-root.component';

const privateRoutes: Routes = [
  {
    path: 'home',
    component: UserHomeComponent,
    children: [
      {path: '', redirectTo: 'dashboard', pathMatch: 'full'}, // uses relative navigation no leading slash
      // references: https://angular.io/guide/router#relative-navigation
      {path: 'dashboard', component: DashBoardComponent},
      {path: 'bindingexamples', component: BindingExamplesComponent},
      {path: 'inputsexamples', component: InputRootComponent},
      {path: 'about', component: AboutComponent}
      ]
  }
  ];


@NgModule({
  imports: [RouterModule.forChild(privateRoutes)],
  exports: [RouterModule]
})
export class PrivateRoutingModule { }

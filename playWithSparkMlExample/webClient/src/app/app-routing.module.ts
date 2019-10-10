import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// importing the components
import {LoginComponent} from './login/login.component';
import {WelcomeComponent} from './welcome/welcome.component';
import {PageNotFoundComponent} from './shared/component/page-not-found/page-not-found.component';

const routes: Routes = [
  {path: '', redirectTo: '/welcome', pathMatch: 'full'}, // Default route
  // This route redirects a URL that fully matches the empty path to the route whose path is '/welcome'.
  {path: 'welcome', component: WelcomeComponent},
  {path: 'login', component: LoginComponent},
  {path: '**', component: PageNotFoundComponent}, // PageNotFound
  /*
   * Important:
   * PrivateModule (submodule with submodule routes) must be place above the AppRoutingModule
   * in the imports array of the AppModule, since path 'home' is not defined in app-routing.module.ts
   * instead in the private-routing.module.ts file
   *
   * If the AppRoutingModule is place above the PrivateModule in the imports array of the app.module.ts file,
   * the defined path in the private-routing module will not be recognized
   * and thus activates the wildcard route in the app-routing.module.ts
   *
   * The component PageNotFoundComponent is then activated instead of the desighed UserHomeComponent.
   */
];

// Find more details about RouterModule https://angular.io/guide/router
@NgModule({
  imports: [RouterModule.forRoot(routes)], // use forRoot(routes, {enableTracking: true}) for debugging purposes
  exports: [RouterModule]
})
export class AppRoutingModule { }

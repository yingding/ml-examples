import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  // private variables used for two way binding.
  private username  = ""; // infered string variable no string type declaration necessary
  private password  = "";
  private errorMessage = "";

  constructor(private router: Router) { }

  ngOnInit() {
  }

  login(): void {
    if (this.username !== "" && this.password !== "") {
      // passing the user name as data parameter to the UserHomeComponent
      this.router.navigate(['/home', {name: this.username}]);
    } else {
      this.errorMessage = "Name or Password is empty!\nPlease type any string for the name and password!";
      // break line with css as exception to inline-block
    }
  }

}

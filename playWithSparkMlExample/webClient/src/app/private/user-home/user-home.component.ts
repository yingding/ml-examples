import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router, ParamMap} from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit {
  private username;

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.username = this.route.snapshot.paramMap.get('name');
    /* one time only, if the query param changes,
     * this.route.paramMap.subscribe or this.route.paramMap.pipe(switchMape()) shall be used.
     * For more details see https://medium.com/@christo8989/angular-6-url-parameters-860db789db85
     */
  }

  logout(): void {
    this.router.navigate(["/welcome"]);
    // this.router.navigate(["/welcome"], {relativeTo: this.route});
    // the use of relativeTo sees not necessary any more in contrary to what is stated in the reference: https://stackoverflow.com/questions/37196882/how-do-i-navigate-to-a-parent-route-from-a-child-route
  }
}

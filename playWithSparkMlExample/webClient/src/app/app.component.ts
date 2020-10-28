import { Component, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  // encapsulation: ViewEncapsulation.None,
})
// https://stackoverflow.com/questions/34542143/load-external-css-style-into-angular-2-component

/* appComponent is necessary as an empty router to switch between welcome, login and dashboard component */
export class AppComponent {
  constructor() {}
}


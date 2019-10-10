import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-dash-board',
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.scss']
})
export class DashBoardComponent implements OnInit {

  private title: any;

  // Requirements: type the content of the value initial in the text box to change the value.
  // Unfortunately it doesn't work.
  // TODO: find the bug here.
  constructor() {
    const initial = { value: 'Angular 7' };
    this.title = { initial }; // Object spread in TypeScript 2.1!
  }

  ngOnInit() {
    console.log('Hello Home component');
  }
}

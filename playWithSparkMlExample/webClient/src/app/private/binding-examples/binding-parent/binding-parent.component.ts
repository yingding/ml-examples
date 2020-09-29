import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-binding-parent',
  templateUrl: './binding-parent.component.html',
  styleUrls: ['./binding-parent.component.scss']
})
export class BindingParentComponent implements OnInit {
  textToChange = 'init text';
  numberToChange = 1;
  clearLog = true;

  constructor() { }

  ngOnInit() {
  }

  newNumber() {
    this.numberToChange++;
  }

  toClearLog() {
    // just switch to make changes happen
    this.clearLog = !this.clearLog;
  }

}


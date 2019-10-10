import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-binding-parent',
  templateUrl: './binding-parent.component.html',
  styleUrls: ['./binding-parent.component.scss']
})
export class BindingParentComponent implements OnInit {
  private textToChange : string = "init text";
  private numberToChange : number = 1;
  private clearLog : boolean = true;

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


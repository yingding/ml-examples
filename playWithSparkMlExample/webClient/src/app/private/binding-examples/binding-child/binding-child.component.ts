import { Component, OnInit, OnChanges, SimpleChange, Input } from '@angular/core';

@Component({
  selector: 'app-binding-child',
  templateUrl: './binding-child.component.html',
  styleUrls: ['./binding-child.component.scss']
})
export class BindingChildComponent implements OnInit, OnChanges {

  // Define the property, which can be bind from the parent component
  // with Input property
  @Input() childText: string;
  @Input() childNumber: number;
  @Input() clearLog: boolean;

  // String Array to stage the changes triggered from parent component
  private changeLogList: string[] = [];

  constructor() { }

  ngOnInit() {
  }

  // ngOnChanges Interface
  ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
    // show the changes structure in console
    console.log(changes);

    // local log variables, created by every onChange
    let log: string[] = [];
    let needClear: boolean = false;
    let changedProp: SimpleChange;

    // get all the 3 property from the changes array
    for (let propertyName in changes) {
      if (propertyName == "clearLog") {
        // set the clear flag
        needClear = true;

      } else {
        // get the SimpleChange object by the propertyName
        changedProp = changes[propertyName];
        let to = JSON.stringify(changedProp.currentValue);
        if (changedProp.isFirstChange()) {
          log.push(`Initial value of ${propertyName} set to ${to}`);
        } else {
          let from = JSON.stringify(changedProp.previousValue);
          log.push(`${propertyName} changed from ${from} to ${to}`);
        }
      }
    }
    // concat multiply changes in log to one string and push as a new log
    // to the global changeLogList
    if (!needClear || changedProp != null && changedProp.isFirstChange) {
      // Fixed bug if only clearLog change is invoked, the changedProp is null
      // and changedProp.isFirstChange is breaking the script.
      this.changeLogList.push(log.join(', '));
    } else {
      // clear
      this.changeLogList = [];
    }
  }

}


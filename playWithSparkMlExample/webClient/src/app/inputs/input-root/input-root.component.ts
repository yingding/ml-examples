import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-input-root',
  templateUrl: './input-root.component.html',
  styleUrls: ['./input-root.component.scss']
})
export class InputRootComponent implements OnInit {
  private componentName: string;
  private moduleName: string;

  constructor() {
    this.moduleName = "InputsModule";
    this.componentName = InputRootComponent.name;
  }

  ngOnInit() {
  }

}

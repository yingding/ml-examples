import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-binding-examples',
  templateUrl: './binding-examples.component.html',
  styleUrls: ['./binding-examples.component.scss']
})
export class BindingExamplesComponent implements OnInit {
  private componentName;

  constructor() {
    this.componentName = BindingExamplesComponent.name;
  }

  ngOnInit() {
  }

}

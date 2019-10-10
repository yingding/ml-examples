import {Component, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit, OnDestroy {
  public projectName: string;

  constructor() {
    this.projectName = 'Angular Demo';
  }

  ngOnInit() { console.log('About::ngOnInit'); }

  ngOnDestroy() { console.log('About::ngOnDestroy'); }

}

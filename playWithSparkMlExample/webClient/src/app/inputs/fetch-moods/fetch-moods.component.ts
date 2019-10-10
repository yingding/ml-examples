import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {MoodModel} from '../models/mood-model';
import {Observable, of} from 'rxjs';
import {MoodsService} from '../services/moods.service';
import {SharedRefreshService} from '../services/shared-refresh.service';
import {catchError} from 'rxjs/operators';


@Component({
  selector: 'app-fetch-moods',
  templateUrl: './fetch-moods.component.html',
  styleUrls: ['./fetch-moods.component.scss']
})
export class FetchMoodsComponent implements OnInit, OnChanges {
  private moods: Observable<MoodModel[]>;
  private refresh: boolean;

  constructor(private moodsService: MoodsService, private sharedRefreshService: SharedRefreshService) {

    // subscribe the observable stream
    this.sharedRefreshService.needUpdate$.subscribe(
      data => {
        console.log('Fetch Component received from sibling/parent: ' + data);
        this.refresh = data;
        // reload
        this.reload();
      });
  }

  ngOnInit() {
    this.reload();
  }
  // this method is nessary to receive the changes
  ngOnChanges(changes: SimpleChanges): void {
  }

  reload() {

    // Angular 6 https://stackoverflow.com/questions/46018259/angular-4-observable-catch-error?rq=1
    this.moods = this.moodsService.getMoods().pipe(
      catchError(error => {
        console.log(error);
        // https://stackoverflow.com/questions/51434236/return-a-null-empty-observable-in-angular-6-with-rxjs-6/53833119#53833119
        return of<MoodModel[]>([]);
      })
    );
  }
}

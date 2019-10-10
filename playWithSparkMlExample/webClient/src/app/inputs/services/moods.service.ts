import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs';
import { map } from 'rxjs/operators';
import {MoodModel} from '../models/mood-model';

// @Injectable({
//    providedIn: InputsModule
// })
@Injectable()
export class MoodsService {
  private API_URL_GET: string = "api/moods/get/";
  private API_URL_POST: string = "api/moods/post/";


  constructor(private http: Http) {
  }

  getMoods(): Observable<MoodModel[]> {
    // csrf-Token for csrf bypass
    const headers = new Headers({'Content-Type': 'application/json', 'Authorization': '', 'Csrf-Token':'nocheck'});
    const httpOptions = new RequestOptions({headers: headers});
    const body = {
      seed:  "seedToChange",
    };
    // the wrapper object from the responsebody has the property name moods, which is an Array
    return this.http.post(this.API_URL_GET, body, httpOptions).pipe(
      map(
      response => response.json().moods as MoodModel[]
      )
    );
  }
  // https://stackoverflow.com/questions/49225275/how-play-sends-csrf-token
  // https://nvisium.com/blog/2017/10/04/play-2-6-security-analysis.html
  // https://github.com/angular/angular/issues/25996
  sendMoods(moods: MoodModel[]): Observable<Response> {
    // construct a request header, for json content
    const headers = new Headers({'Content-Type': 'application/json', 'Authorization': '', 'Csrf-Token':'nocheck'});
    const httpOptions = new RequestOptions({headers: headers});
    const body = {
      seed:  "seedToChange",
      moods: moods,
    };
    return this.http.post(this.API_URL_POST, body, httpOptions);
  }
}

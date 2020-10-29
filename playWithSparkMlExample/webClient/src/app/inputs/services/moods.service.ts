import {Injectable} from '@angular/core';
// deprecated http
// import { Http, Response, Headers, RequestOptions} from "@angular/http";
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http'; // need to import HttpClientModule first
import {Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {MoodModel} from '../models/mood-model';

@Injectable({
    providedIn: 'root'
})
// @Injectable({
//   providedIn: 'any'
// })
export class MoodsService {
  private API_URL_GET = 'api/moods/get/';
  private API_URL_POST = 'api/moods/post/';


  constructor(private http: HttpClient) {
  }

  /**
   * Reference guide of using httpClient - https://angular.io/guide/http
   */
  getMoods(): Observable<MoodModel[]> {
    // csrf-Token for csrf bypass
    const headers = new HttpHeaders({'Content-Type': 'application/json', Authorization: '', 'Csrf-Token': 'nocheck'});
    // const httpOptions = new RequestOptions({headers: headers});
    // object literal shorthand, in typescript when an already-defined varaible is given the same name within a new object.
    // {headers: headers} use {headers} instead
    const httpOptions = {
      headers,
    };
    const body = {
      seed:  'seedToChange',
    };
    return this.http.post(this.API_URL_GET, body, httpOptions).pipe(
      map(
        bodyJson => {
          // object is more restrict than any, cast Object to any to have property invocation
          return ((bodyJson as any).moods) as MoodModel[];
        }
      )
    );
    // the wrapper object from the response body has the property name moods, which is an Array

    // return this.http.post(this.API_URL_GET, body, httpOptions).pipe(
    //   map(
    //     response => response.json().moods as MoodModel[]
    //   )
    // );
  }

  /**
   * https://stackoverflow.com/questions/49225275/how-play-sends-csrf-token
   * https://nvisium.com/blog/2017/10/04/play-2-6-security-analysis.html
   * https://github.com/angular/angular/issues/25996
   * Object or {} vs any
   * https://stackoverflow.com/questions/18961203/any-vs-object/18961904#18961904
   * @param moods: moods array needs to be added to the server
   * @return Observable HttpResponse with body type string
   */
  sendMoods(moods: MoodModel[]): Observable<HttpResponse<string>> {
    // construct a request header, for json content
    const headers = new HttpHeaders({'Content-Type': 'application/json', Authorization: '', 'Csrf-Token': 'nocheck'});
    // const httpOptions = new RequestOptions({headers: headers});
    // tslint:disable-next-line:max-line-length
    // customize the response get from the HttpClient https://stackoverflow.com/questions/45505619/angular-4-3-3-httpclient-how-get-value-from-the-header-of-a-response
    // const httpOptions = {
    //   headers,
    //   observe: 'response'
    // };
    // use object literal shorthand in Typescript {moods} instead of {moods: moods}
    const body = {
      seed:  'seedToChange',
      moods,
    };
    // notice: ts lint has issue to have httpOptions const pass, just type in the post directly
    // https://angular.io/guide/http
    // tslint:disable-next-line:max-line-length
    // https://stackoverflow.com/questions/57404556/angular-httpclient-error-with-empty-200-201-response-always-calls-json-parse/57405012#57405012
    // since this endpoint returns Content-Type text/plain, thus the default 'json' response must be changed, otherwise subscribe will
    // run into error with response.ok false, even status 200
    return this.http.post(this.API_URL_POST, body, {headers, observe: 'response', responseType: 'text'});
  }
}

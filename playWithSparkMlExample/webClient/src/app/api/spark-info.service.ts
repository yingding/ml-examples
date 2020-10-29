import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import HttpUtils from './http.utils';
import {catchError} from 'rxjs/operators';

export interface SparkInfo {
  isActivated: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class SparkInfoService {
  private API_URL_GET = 'api/sparkinfo/get/';
  constructor(private http: HttpClient) { }

  getSparkInfo(): Observable<SparkInfo> {
    return this.http.post<SparkInfo>(
      this.API_URL_GET,
      HttpUtils.genSeedHttpBody(),
      HttpUtils.genHttpOptions(HttpUtils.genJsonHttpHeaders())
    ).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    if (error.status === 504) {
      return throwError('Backend Service temporarily not available!');
    } else {
      return throwError(
        'Something bad happened; please try again later.');
    }
  }
}

import { Component, OnInit } from '@angular/core';
import {MoodModel} from '../models/mood-model';
import * as moment from 'moment';
import {MoodsService} from '../services/moods.service';
import {SharedRefreshService} from '../services/shared-refresh.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-input-mood',
  templateUrl: './input-mood.component.html',
  styleUrls: ['./input-mood.component.scss']
})
export class InputMoodComponent implements OnInit {

  private moods: MoodModel[] = [];
  private responseMessage: string = null;
  private responseMessageColor: string = null;
  private refresh = false;
  private mood = '';

  constructor(private moodService: MoodsService, private sharedRefreshService: SharedRefreshService) {
  }

  ngOnInit() {
  }

  cacheData(inputElement: HTMLInputElement) {
    // clear response message
    this.responseMessage = null;
    this.responseMessageColor = null;

    // moment format can be found https://momentjs.com/docs/#/displaying/
    const now = moment().format('dddd, MMMM Do YYYY, h:mm:ss a');
    const nowTimeStamp = moment().utc().valueOf();
    if (this.mood !== '') { // is empty
      const currentMood = new MoodModel(nowTimeStamp, this.mood.toUpperCase());
      console.log('currentDateString: ', now);
      console.log('currentUTCtimestamp: ', currentMood.timestamp);
      console.log('call service: ', currentMood.mood);
      this.moods.push(currentMood);
      inputElement.value = null; // clean the value in the html input
      this.mood = '';
    } else {
      this.responseMessage = 'mood is empty';
      this.responseMessageColor = 'yellow';
    }
  }


  clearCachedMoods() {
    this.moods = [];
  }

  sendData() {
    if (this.moods.length !== 0) {
      this.moodService.sendMoods(this.moods).subscribe(
        response => {
          // console.log('subscribe next');
          this.handleResponse(response);
        },
        errorResponse => {
          // if the HttpErrorResponse is called, response.ok is false even the status is 2xx
          // console.log('subscribe error');
          this.handleErrorResponse(errorResponse);
        }
      );
      // put refresh in side success call back
      // refresh is true
      // this.refresh = !this.refresh; // switch
      // this.sharedRefreshService.publishData(this.refresh);
    } else {
      this.responseMessage = 'no moods is stored in local cache';
      this.responseMessageColor = 'yellow';
    }
  }
  /* error response has no body */
  handleErrorResponse(response: HttpErrorResponse) {
    this.responseMessage = response.statusText;
    this.responseMessageColor = 'red';
    if (response.error !== undefined) {
        this.responseMessage += `: ${response.error}`;
    }
  }
  handleResponse(response: HttpResponse<string>) {

    /* set the message to give user a feedback of upload */
    this.responseMessage = response.statusText;
    if (response.body !== undefined) {
      // use typescript template string
      this.responseMessage += `: ${response.body}`;
    }
    // response.ok is false, even when response.status is 200, since the response content type is set as text/pain from server
    // but the http default options of httpClient.post for responseType is json
    // if (response.status === 200) {
    /* change the color of response message */
    if (response.ok) { // successful and no error in httpclient
      this.responseMessageColor = 'lawngreen';
      this.moods = []; // clear the moods
      // triggers the refresh event
      this.refresh = !this.refresh; // switch
      this.sharedRefreshService.publishData(this.refresh);
    }
  }

}

import { Component, OnInit } from '@angular/core';
import {MoodModel} from '../models/mood-model';
import * as moment from 'moment';
import {MoodsService} from '../services/moods.service';
import {SharedRefreshService} from '../services/shared-refresh.service';
import {Response} from '@angular/http';

@Component({
  selector: 'app-input-mood',
  templateUrl: './input-mood.component.html',
  styleUrls: ['./input-mood.component.scss']
})
export class InputMoodComponent implements OnInit {

  private moods: MoodModel[] = [];
  private responseMessage: string = null;
  private responseMessageColor: string = null;
  private refresh: boolean = false;
  private mood: string = "";

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
    if (this.mood != "") { // is empty
      const currentMood = new MoodModel(nowTimeStamp, this.mood.toUpperCase());
      console.log("currentDateString: ", now);
      console.log("currentUTCtimestamp: ", currentMood.timestamp);
      console.log("call service: ", currentMood.mood);
      this.moods.push(currentMood);
      inputElement.value = null; // clean the value in the html input
      this.mood = "";
    } else {
      this.responseMessage = "mood is empty";
      this.responseMessageColor ="yellow";
    }
  }


  clearCachedMoods() {
    this.moods = [];
  }

  sendData() {
    if (this.moods.length !== 0) {
      this.moodService.sendMoods(this.moods).subscribe(
        response => this.handleResponse(response),
        error => this.handleResponse(error)
      );
      // put refresh in side success call back
      // refresh is true
      // this.refresh = !this.refresh; // switch
      // this.sharedRefreshService.publishData(this.refresh);
    } else {
      this.responseMessage = "no moods is stored in local cache";
      this.responseMessageColor ="yellow";
    }
  }

  handleResponse(response: Response) {
    if (response.ok) {
      this.responseMessage = response.text();
      this.responseMessageColor = 'lawngreen';
      this.moods = []; // clear the moods

      // refresh is true
      this.refresh = !this.refresh; // switch
      this.sharedRefreshService.publishData(this.refresh);

    } else {
      this.responseMessage = response.text();
      this.responseMessageColor = 'red';
    }
  }

}

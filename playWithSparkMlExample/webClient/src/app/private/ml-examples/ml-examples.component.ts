import { Component, OnInit } from '@angular/core';
import { SparkInfoService, SparkInfo} from '../../api/spark-info.service';

@Component({
  selector: 'app-ml-examples',
  templateUrl: './ml-examples.component.html',
  styleUrls: ['./ml-examples.component.scss']
})
export class MlExamplesComponent implements OnInit {
  sparkInfo: SparkInfo;
  errorText: string = null;
  errorTextColor: string;
  constructor(private sparkInfoService: SparkInfoService) { }

  ngOnInit(): void {
    this.asyncReload();
  }

  asyncReload() {
    this.sparkInfoService.getSparkInfo().subscribe(
      (data: SparkInfo) => {
        this.sparkInfo = data;
      },
      (errorText: any) => {
        // https://www.learnrxjs.io/learn-rxjs/operators/error_handling/catch
        // subscribe catchError, and error is the text passed by throwError(error)
        if (errorText) {
          this.errorText = errorText;
          this.errorTextColor = 'red';
        }
      }
    );
  }
}

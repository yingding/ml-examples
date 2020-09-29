import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {PopupDialogComponent} from '../shared/dialog/popup-dialog/popup-dialog.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {
  title = 'WelcomeComponent';

  successStr = 'Your setup works fine!';

  ngOnInit() {
  }

  constructor(private router: Router, public dialog: MatDialog) {}

  openDialog(): void {
    const dialogRef = this.dialog.open(PopupDialogComponent, {
      width : '250px',
      data : {text: this.successStr}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // data.text is given back as result.
      // console.log(result)
    });
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }

}

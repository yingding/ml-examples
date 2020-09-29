import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrivateRoutingModule } from './private-routing.module';
import { DashBoardComponent } from './dash-board/dash-board.component';
import { AboutComponent } from './about/about.component';
import { UserHomeComponent } from './user-home/user-home.component';

// material input
import { FormsModule} from '@angular/forms'; // FormsModule is needed for the two binding for the Form in AngularMaterial
// material design
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { BindingExamplesComponent } from './binding-examples/binding-examples.component';
import { BindingParentComponent } from './binding-examples/binding-parent/binding-parent.component';
import { BindingChildComponent } from './binding-examples/binding-child/binding-child.component';
import { InputsModule } from '../inputs/inputs.module';


@NgModule({
  declarations: [
    DashBoardComponent, AboutComponent, UserHomeComponent, BindingExamplesComponent, BindingParentComponent, BindingChildComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    InputsModule, // get the InputsRootComponent
    PrivateRoutingModule,
  ]
})
export class PrivateModule { }

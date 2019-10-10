import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InputMoodComponent } from './input-mood.component';

describe('InputMoodComponent', () => {
  let component: InputMoodComponent;
  let fixture: ComponentFixture<InputMoodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InputMoodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InputMoodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

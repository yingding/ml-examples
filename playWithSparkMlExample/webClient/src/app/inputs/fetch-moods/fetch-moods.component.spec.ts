import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchMoodsComponent } from './fetch-moods.component';

describe('FetchMoodsComponent', () => {
  let component: FetchMoodsComponent;
  let fixture: ComponentFixture<FetchMoodsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FetchMoodsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FetchMoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

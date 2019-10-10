import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BindingChildComponent } from './binding-child.component';

describe('BindingChildComponent', () => {
  let component: BindingChildComponent;
  let fixture: ComponentFixture<BindingChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BindingChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BindingChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

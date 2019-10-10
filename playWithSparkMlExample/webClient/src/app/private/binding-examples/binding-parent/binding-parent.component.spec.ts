import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BindingParentComponent } from './binding-parent.component';

describe('BindingParentComponent', () => {
  let component: BindingParentComponent;
  let fixture: ComponentFixture<BindingParentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BindingParentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BindingParentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

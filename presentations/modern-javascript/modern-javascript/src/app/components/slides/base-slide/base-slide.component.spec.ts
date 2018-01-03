import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseSlideComponent } from './base-slide.component';

describe('BaseSlideComponent', () => {
  let component: BaseSlideComponent;
  let fixture: ComponentFixture<BaseSlideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaseSlideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseSlideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

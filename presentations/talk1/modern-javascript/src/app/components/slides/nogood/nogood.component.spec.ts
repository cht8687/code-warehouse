import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NogoodComponent } from './nogood.component';

describe('NogoodComponent', () => {
  let component: NogoodComponent;
  let fixture: ComponentFixture<NogoodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NogoodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NogoodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

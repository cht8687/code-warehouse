import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { dispatch, select } from '@angular-redux/store';
import $ from 'jquery';
import { Observable } from 'rxjs/Observable';
import { 
  INTRO
} from '../../constants/slides';
import { PresentionActions } from '../../actions/presentationActions';

import { IntroComponent } from '../../components/slides/intro/intro.component';
import { FrameworksComponent } from '../../components/slides/frameworks/frameworks.component';
@Component({
  selector: 'app-presentation',
  templateUrl: './presentation.component.html',
  styleUrls: ['./presentation.component.scss']
})
export class PresentationComponent implements OnInit {
  // Selecting an immutable object
  @select(['presentation', 'slideIndex']) 
  readonly slideIndex$: Observable<number>;
  @select(['presentation', 'currentSlide']) 
  readonly currentSlide$: Observable<number>;
  @dispatch() increment = () => ({ type: PresentionActions.NEXT_SLIDE });
  @dispatch() decrement = () => ({ type: PresentionActions.PREV_SLIDE });

  constructor() {
  }

  ngOnInit() {}

  ngAfterViewChecked() { 
    $(document).on('click', this.documentClickHandler); 
  }

  documentClickHandler(e) {
    console.log(1);
    let windowSize = 0;
    let currentPosX = 0;
    let right = 0;

    windowSize = $(window).width();
    currentPosX = e.pageX;
    right = Math.round(currentPosX / windowSize);

    if (e.which === 39 || right) {
       this.increment();
    } else if (e.which === 37 || !right) {
       this.decrement();
    }
  }
}

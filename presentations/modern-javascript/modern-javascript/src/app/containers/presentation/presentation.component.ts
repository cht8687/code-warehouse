import { Component, OnInit } from '@angular/core';
import { select } from '@angular-redux/store';
import $ from 'jquery';
import { Observable } from 'rxjs/Observable';
import { 
  INTRO
} from '../../constants/slides';
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

  constructor() {
  }

  ngOnInit() { 
    $(document).on('click', this.documentClickHandler); 
  }

  documentClickHandler() {
    
  }
}

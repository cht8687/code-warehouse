import { Component } from '@angular/core';
import { select } from '@angular-redux/store';
import { Observable } from 'rxjs/Observable';
import { 
  INTRO
} from './constants/slides';
import { IntroComponent } from './components/slides/intro/intro.component';
import { FrameworksComponent } from './components/slides/frameworks/frameworks.component';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  // Selecting an immutable object
  @select(['presentation', 'slideIndex']) 
  readonly slideIndex$: Observable<number>;
  
  @select(['presentation', 'currentSlide']) 
  readonly currentSlide$: Observable<number>;

  constructor() {
  }
}

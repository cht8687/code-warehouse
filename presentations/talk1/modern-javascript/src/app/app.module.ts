import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgReduxModule } from '@angular-redux/store';
import { NgReduxRouterModule } from '@angular-redux/router';

import { AppComponent } from './app.component';

// This app's ngModules
import { StoreModule } from './store/module';
import { IntroComponent } from './components/slides/intro/intro.component';
import { FrameworksComponent } from './components/slides/frameworks/frameworks.component';
import { PresentationComponent } from './containers/presentation/presentation.component';
import { GitComponent } from './components/slides/git/git.component';
import { LintComponent } from './components/slides/lint/lint.component';
import { FpComponent } from './components/slides/fp/fp.component';
import { CodereviewComponent } from './components/slides/codereview/codereview.component';
import { NogoodComponent } from './components/slides/nogood/nogood.component';

@NgModule({
  declarations: [
    AppComponent,
    IntroComponent,
    FrameworksComponent,
    PresentationComponent,
    GitComponent,
    LintComponent,
    FpComponent,
    CodereviewComponent,
    NogoodComponent
  ],
  imports: [
    BrowserModule,
    NgReduxModule,
    NgReduxRouterModule,
    StoreModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

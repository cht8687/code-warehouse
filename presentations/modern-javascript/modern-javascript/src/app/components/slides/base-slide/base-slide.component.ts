import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-base-slide',
  templateUrl: './base-slide.component.html',
  styleUrls: ['./base-slide.component.scss']
})
export class BaseSlideComponent implements OnInit {

  hidden:boolean = false

  constructor() { }

  ngOnInit() {
  }
}

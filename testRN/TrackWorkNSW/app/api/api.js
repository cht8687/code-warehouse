'use strict'
import  * as url  from '../constant/url';

export default class Api {

  getNews() {
    return fetch(url.news)
     .then(res => res.text())
     //.then(res => window.console.log(res))
  }
}

/*
  Implementation of Report object
*/
import Bloomfilter from './Bloomfilter';
import { randomWords } from './RandomWords';
export default class Report {
  constructor(bufferSize, numHashes, randomWordsNum) {
    this._bloom = new Bloomfilter(bufferSize, numHashes);
    this._dictionary = [];
    this._randomWordsNum = Number(randomWordsNum);
    this._randomWords = [];
    this._bufferSize = bufferSize;
    this._numHash = numHashes;
    this._falsePositiveCounter = 0;
    this.generateWords();
  }

  loadDictionary(data) {
    this._dictionary = data;
    for (const i in data) {
      this._bloom.add(data[i]);
    }
  }

  generateWords() {
    this._randomWords = randomWords(this._randomWordsNum);
  }
  generateReport() {
    for(const i in this._randomWords) {
      const result = this.bloomCheck(this._randomWords[i]);
      if (result) {
        this.checkWithDictionary(this._randomWords[i]);
      }
    }
    this.printNiceTable();
  }

  bloomCheck(s) {
    return this._bloom.check(s);
  }

  checkWithDictionary(s) {
    if(this._dictionary.indexOf(s) < 0) {
      this._falsePositiveCounter++;
    }
  }

  printNiceTable() {

    const rate = (this._falsePositiveCounter / this._randomWordsNum).toFixed(2);
    console.log('********report*****************');
    console.log(`Dictionary size: ${this._dictionary.length}`);
    console.log(`BitBuffer size: ${this._bufferSize}`);
    console.log(`Number of Hash Functions: ${this._numHash}`);
    console.log(`Generated words: ${this._randomWordsNum}`);
    console.log(`False Positive count: ${this._falsePositiveCounter}`);
    console.log(`Error Rate: %${rate}`);
  }
}

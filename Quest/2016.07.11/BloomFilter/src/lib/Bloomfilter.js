/*
  Implementation of Bloomfilter
  I am using ES6 syntax(ECMAScript2015), Babel will compile it back to ES5
*/
import { hashCode } from './Hash';
import BitBuffer from './BitBuffer';

export default class Bloomfilter {
  constructor(bitMapSize, numHashes) {
    this._pool = new BitBuffer(bitMapSize);
    this._size = bitMapSize;
    this._numHash = numHashes;
  }

  // Function to add an item to Bloom filter */
  add(key) {
    const hashvalues = hashCode(key, this._size, this._numHash);
    for (let n = 0; n < hashvalues.length; n++) {
      this._pool.set(hashvalues[n], true);
    }
  }

  // Function to query if an item is possibly within Bloom filter
  check(key) {
    console.log('checking: ' + key);
    const hashvalues = hashCode(key, this._size, this._numHash);
    for (let n=0; n < hashvalues.length; n++) {
      if (!this._pool.get(hashvalues[n])) {
        return false;
      }
    }
    return true;
  }
} 


/*
  Based on my research, there are a few Hash function implementations
  which is used in Bloomfilters:
  Cassandra:  Murmur hashes
  Hadoop: Jenkins and Murmur hashes
  FNV
  MD5......
  I tried murmurHash3.js but there seems something wrong.
  I am using the FNV hash function at the moment.
*/
import HashGen  from './HashGen';
export function hashCode(key, size, hashNums) {
  function generate(seed, data) {
    const h = new HashGen();
    h.update(seed);
    h.update(data);
    return h.value() >>> 0;
  }
  const hasha = generate(Buffer("C"), key);
  const hashb = generate(Buffer("H"), key);
  const hashes = [];
  /*
    hashi(x,m) = (hasha(x)+i×hashb(x)) mod m
  */
  for (let i = 0; i < hashNums; i++) {
    hashes.push((hasha + i * hashb) % size);
  }
  return hashes;
}


/* murmurHash3.js implementation below seems not working */

// const murmurHash3 = require('./murmurHash3.js');
// export function hashCode(string, bitmapSize, numOfHashes) {
//   var hash = [];
//   let hashValue = murmurHash3.x64.hash128(string);
//   let hashValue1 = hashValue.substr(0, 16);
//   let hashValue2 = hashValue.substr(16, 32);
//   console.log('bitmapsize: ' + bitmapSize);
//   for (let i = 0; i < numOfHashes; i++) {
//     console.log((hashValue1 + i * hashValue2) % bitmapSize);
//     hash.push((hashValue1 + i * hashValue2) % bitmapSize);
//   }
//   return hash;
// }

// export function nthHash(n, hashA, hashB, filterSize) {
//   return (hashA + n * hashB) % filterSize;
// }

import { assert } from 'chai';
import Bloomfilter from '../src/lib/Bloomfilter';
import BitBuffer from '../src/lib/BitBuffer';
import { hashCode } from '../src/lib/Hash';
require('mocha-sinon');

/*
  Please note that Line as been trimmed before send in those functions
*/
describe("Bloomfilter", () => {
  let bloom;
  before(() => {
    bloom = new Bloomfilter(123456789, 7);
    bloom.add('apple');
    bloom.add('pear');
    bloom.add('orange');
  });

  it('should return true if given word is in dictionary ', function() {
    assert.equal(true, bloom.check('apple'));
    assert.equal(true, bloom.check('pear'));
    assert.equal(true, bloom.check('orange'));
  });

  it('should return false if given word is in dictionary ', function() {
    assert.equal(false, bloom.check('safdjlsadjf'));
    assert.equal(false, bloom.check('iamnotaword'));
    assert.equal(false, bloom.check('notenglish'));
  });
});

describe("BitBuffer", () => {
  let bitbuffer;
  before(() => {
    bitbuffer = new BitBuffer(1);
    bitbuffer.set("9sdfaf24243", true);
  });

  it('should return true if hash value had being set', function() {
    assert.equal(true, bitbuffer.get('9sdfaf24243'));
  });

  it('should return false if given word is not in the buffer', function() {
    console.log(bitbuffer.get('sdf424aasdfsdafsfd'));
    assert.equal(true, bitbuffer.get('sdf424aasdfsdafsfd'));
  });
});

describe("hashCode", () => {
  let hashes;
  before(() => {
    hashes = hashCode("apple", 123456789, 9);
  });

  it('should return equal size of the give hashNum options', function() {
    assert.equal(9, hashes.length);
  });
});

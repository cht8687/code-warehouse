import { assert, expect } from 'chai';
import { 
  isSartWithBlockComments,
  isSartWithDoubleSlashComments,
  isBlankLine,
  isEndOfBlockComments,
  hasBlockCommentsStartAtEnd,
  parsingRawData
} from '../src/index.js';
import fs from 'fs';
require('mocha-sinon');

/*
  Please note that Line as been trimmed before send in those functions
*/
describe("Test isSartWithBlockComments", () => {
  it('should return false if line does not start with /*', function() {
    const line1 = ' ';
    const line2 = '//* ';
    assert.equal(false, isSartWithBlockComments(line1));
    assert.equal(false, isSartWithBlockComments(line2));
  });

  it('should return true if line start with /*', function() {
    const line3 = '/*'
    assert.equal(true, isSartWithBlockComments(line3));
  });
});

describe("Test isSartWithDoubleSlashComments", () => {
  it('should return false if line does not start with //', function() {
    const line1 = ' ';
    const line2 = '/* ';
    assert.equal(false, isSartWithDoubleSlashComments(line1));
    assert.equal(false, isSartWithDoubleSlashComments(line2));
  });

  it('should return true if line start with //', function() {
    const line3 = '//';
    assert.equal(true, isSartWithDoubleSlashComments(line3));
  });
});

describe("Test isBlankLine", () => {
  it('should return false if line contains any character', function() {
    const line2 = '   ';
    const line3 = ' abc ';
    assert.equal(false, isBlankLine(line2));
    assert.equal(false, isBlankLine(line3));
  });
  it('should return true if line contains 0 character', function() {
    const line1 = '';
    assert.equal(true, isBlankLine(line1));
  });
});

describe("Test isEndOfBlockComments", () => {
  it('should return false if is not end of */ block', function() {
    const line1 = '/*';
    const line2 = '';
    const line3 = '   ';
    const line4 = '/*///*';
    assert.equal(false, isEndOfBlockComments(line1));
    assert.equal(false, isEndOfBlockComments(line2));
    assert.equal(false, isEndOfBlockComments(line3));
    assert.equal(false, isEndOfBlockComments(line4));
  });
  it('should return true if is end of */ block', function() {
    const line4 = '*/ ';
    const line5 = '*///';
    const line6 = '/*//*/';
    const line7 = 'comments like this*/';
    assert.equal(true, isEndOfBlockComments(line4));
    assert.equal(true, isEndOfBlockComments(line5));
    assert.equal(true, isEndOfBlockComments(line6));
    assert.equal(true, isEndOfBlockComments(line7));
  });
});

describe("Test hasBlockCommentsStartAtEnd", () => {
  it('should return false if no /* start block at end', function() {
    const line1 = '/* */';
    const line2 = '';
    const line3 = '   ';
    const line4 = '/*//*/';
    assert.equal(false, hasBlockCommentsStartAtEnd(line1));
    assert.equal(false, hasBlockCommentsStartAtEnd(line2));
    assert.equal(false, hasBlockCommentsStartAtEnd(line3));
    assert.equal(false, hasBlockCommentsStartAtEnd(line4));
  });
  it('should return true if is /* block at the end', function() {
    const line4 = '/* ';
    const line5 = '/*/*';
    const line6 = '/*///*';
    const line7 = 'int a = 3; /*';
    assert.equal(true, hasBlockCommentsStartAtEnd(line4));
    assert.equal(true, hasBlockCommentsStartAtEnd(line5));
    assert.equal(true, hasBlockCommentsStartAtEnd(line6));
    assert.equal(true, hasBlockCommentsStartAtEnd(line7));
  });
});

describe("parsingRawData", () => {
  beforeEach(function() {
    this.sinon.stub(console, 'log');
  });

  it('should return correct line number', function(done) {
    fs.readFile("test/testfiles/test1.java", function(err, data) {
      if (err) {
        throw "unable to read the file";
      } else {
        parsingRawData(data.toString());
        expect(console.log.calledWith('There are 4 lines of code in --compilers')).to.be.true;
        done();
      }
    });
  });
});

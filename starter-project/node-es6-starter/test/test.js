import { assert, expect } from 'chai';
import { 
  index
} from '../src/index.js';
import fs from 'fs';
require('mocha-sinon');

/*
  Please note that Line as been trimmed before send in those functions
*/
describe("Test index", () => {
  it('should return true', function() {
    assert.equal(false, index());
    assert.equal(false, index());
  });
});

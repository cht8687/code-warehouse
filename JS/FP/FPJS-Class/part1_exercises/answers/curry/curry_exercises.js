require('../../support');
var _ = require('ramda');


// Exercise 1
//==============

var words = _.split(' ');

// Exercise 1a
//==============

var sentences = _.map(words);


// Exercise 2
//==============

var filterQs = _.filter(_.match(/q/i));


// Exercise 3
//==============
// Use the helper function _keepHighest to refactor max

var _keepHighest = function(x,y){ return x >= y ? x : y; }; // <- leave be

var max = _.reduce(_keepHighest, 0);


// Bonus 1:
// ============
// wrap array's slice to be functional and curried.
// //[1,2,3].slice(0, 2)
var slice = _.curry(function(start, end, xs){ return xs.slice(start, end); });


// Bonus 2:
// ============
// use slice to define a function "take" that takes n elements. make it's curried
var take = slice(0);


module.exports = { words: words,
                   sentences: sentences,
                   filterQs: filterQs,
                   max: max,
                   slice: slice,
                   take: take
                 };

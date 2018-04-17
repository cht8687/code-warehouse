import Bloomfilter from './lib/Bloomfilter';
import fs from 'fs';

function listenToCommandInput(words) {
  // Init bloomfilter
  const bloom = new Bloomfilter(123456789, 7);

  //adding all words into the filter
  for (const i in words) {
    bloom.add(words[i]);
  }

  process.stdin.resume();
  process.stdin.setEncoding('utf8');
  const util = require('util');
  console.log("Type in a word to check: ");
  process.stdin.on('data', function (text, done) {
    console.log('received data:', util.inspect(text.trim()));
    if (text === 'quit\n') {
      done();
    }
    const query = text.trim();
    if (bloom.check(query)) {
        console.log("word " + query + " possibly in.");
    } else {
        console.log("word is not in");
    }
  });
}

function loadDictionary(filename) {
  fs.readFile(filename, function(err, data) {
    if (err) {
      console.error("Unable to read the file, check the file path and name.");
    } else {
      console.log("Setting up bloomfilter......");
      const words = data.toString().split('\n');
      listenToCommandInput(words);
    }
  });
}

function processCommandLine(argv) {
  const [a, b, ...args] = argv;
  if (args.length === 0) {
    console.error("Please type correct command: babel-node index.js dictionary/xxx.txt");
  } else {
    console.log("Loading dictionary.......");
    loadDictionary(String(args));
  }
}

// Program main entry point:
processCommandLine(process.argv);

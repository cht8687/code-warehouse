import Report from './lib/Report';
import fs from 'fs';
function printCommand() {
  console.log("Type in [buffersize, hash function number, words generate] separate by space.");
  console.log("e.g.: 12345678  9 1000");
}


function performTest(userCommands) {
  console.log("Setting up test enviroment..............");
  const bitBufferSize = userCommands[0];
  const hashFunctionNumber = userCommands[1];
  const numberOfWordsGenerate = userCommands[2];
  const report = new Report(bitBufferSize, hashFunctionNumber, numberOfWordsGenerate);
  fs.readFile("src/dictionary/wordlist.txt", function(err, data) {
    if (err) {
      console.error("Unable to read the file, check the file path and name.");
    } else {
      const words = data.toString().split('\n');
      report.loadDictionary(words);
      report.generateReport();
      printCommand();
    }
  });
}

function part2CheckFalseNegetiveReport() {
  process.stdin.resume();
  process.stdin.setEncoding('utf8');
  const util = require('util');
  let canPerformReport = false;
  printCommand();
  process.stdin.on('data', function (text, done) {
    console.log('received command', util.inspect(text.trim()));
    if (text === 'quit\n') {
      done();
    } else if ( text !== null && text.trim().split(' ').length === 3 ) {
      const userCommands = text.trim().split(' ');
      console.log(userCommands);
      canPerformReport = true;
      for (let i = 0 ; i < userCommands.length; i++) {
        if (isNaN(Number(userCommands[i]))) {
          canPerformReport = false;
          break;
        }
      }
      if (canPerformReport) {
        performTest(userCommands);
      }
    }
    if (!canPerformReport) {
      console.log("Wrong command detected!");
      printCommand();
    }
  });
}

function processCommandLine() {
  part2CheckFalseNegetiveReport();
}

// Program main entry point:
processCommandLine(process.argv);

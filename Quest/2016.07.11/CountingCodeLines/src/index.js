import fs from 'fs';

// check if line is start with /*
export function isSartWithBlockComments(line) {
  return line.indexOf('/*') === 0;
};

// check if line is start with //
export function isSartWithDoubleSlashComments(line) {
  return line.indexOf('//') === 0;
};

export function isBlankLine(line) {
  return line.length === 0;
};

// check if line is the end of comments block
export function isEndOfBlockComments(line) {
  // if contains ending */
  if (line.indexOf('*/') >= 0) {
    // check if there are another comments block after it
    if (line.lastIndexOf('/*') >= 0) {
      if (line.lastIndexOf('/*') > line.lastIndexOf('*/')) {
        return false;
      }
    }
    return true;
  }
  return false;
};

//  For situations like:  int a = 3; /*
//                this is a two line comments ***/
export function hasBlockCommentsStartAtEnd(line) {
  // if has /* 
  if (line.lastIndexOf('/*') >= 0) {
      // if there has no */ to end it
      if (line.lastIndexOf('/*') < line.lastIndexOf('*/')) {
        return false;
      }
      return true;
  }
  return false;
}

/*
  Core parsing funtion.
  step1: strim all spaces and translate to none empty lines
  step2: check if is block comments or double slash comments
*/
export function parsingRawData (input) {
  // cut all lines and trim all the whitespaces
  const lines = input.split('\n').map(x => x.trim());
  let lineCounter = 0;
  let isCommitBlock = false;
  // loop through each line
  for ( let i = 0; i < lines.length; i++ ) {
    if (isCommitBlock) {
      if (isEndOfBlockComments(lines[i])) {
        isCommitBlock = false;
      }
      continue;
    }
    if (isSartWithBlockComments(lines[i])) {
      isCommitBlock = true;
      continue;
    } else if (isSartWithDoubleSlashComments(lines[i])) {
      continue;
    } else if (isBlankLine(lines[i])) {
      continue;
    } else {
      lineCounter++;
      if (hasBlockCommentsStartAtEnd(lines[i])) {
        isCommitBlock = true;
      }
    }
  }
  console.log("There are " + lineCounter + " lines of code in " + process.argv[2]);
};

function readFileContent(filename) {
  fs.readFile(filename, function(err, data) {
    if (err) {
     console.error("Unable to read the file, check the file path and name.");
    } else {
      parsingRawData(data.toString());
    }
  });
};

function processCommandLine(argv) {
  const [a, b, ...args] = argv;
  if (args.length === 0) {
    console.error("Please type correct command: babel-node index.js filename(xxx.java)");
  } else {
    readFileContent(String(args));
  }
};

// Program main entry point:
processCommandLine(process.argv);

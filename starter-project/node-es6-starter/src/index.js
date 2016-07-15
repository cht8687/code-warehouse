import fs from 'fs';

export function index() {
  return true;
}

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

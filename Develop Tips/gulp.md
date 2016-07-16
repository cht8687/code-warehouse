##Creating an ES6 gulpfile

```bash

npm install babel-core babel-preset-es2015 --save-dev


touch .babelrc


```

add

```bash

{
  "presets": ["es2015"]
}

```

 rename the `gulpfile.js` to `gulpfile.babel.js`


now can use ES6:

 ```bash

 'use strict';

import gulp from 'gulp';
import sass from 'gulp-sass';
import autoprefixer from 'gulp-autoprefixer';
import sourcemaps from 'gulp-sourcemaps';

const dirs = {
  src: 'src',
  dest: 'build'
};

const sassPaths = {
  src: `${dirs.src}/app.scss`,
  dest: `${dirs.dest}/styles/`
};

gulp.task('styles', () => {
  return gulp.src(paths.src)
    .pipe(sourcemaps.init())
    .pipe(sass.sync().on('error', plugins.sass.logError))
    .pipe(autoprefixer())
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(paths.dest));
});

```

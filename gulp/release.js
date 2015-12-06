
var destFolderPath = '../cht8687.github.io/react-webpack-starter-kit/example';
var gulp = require('gulp');

gulp.task('default',['moveToDest']);





gulp.task('moveToDest', function() {
	return gulp.src('dist/*.js')
		.pipe(gulp.dest(destFolderPath))
});


const constants = require('./constants');
const functions = require('./loginRoute');

const app = constants.express();

app.get('/login', (request, response) => {

	let studentId = request.query.studentId;
	let studentPassword = request.query.studentPassword;

	functions.checkStudentIdExistsOrNot(studentId, studentPassword, response, constants);

});

app.listen(constants.port, () => {
	console.log(`Server started on: http://localhost:${constants.port}`);
});

const constants = require('./constants');
const loginFunctions = require('./loginRoute');
const contactFunctions = require('./contactRoute');

const app = constants.express();

app.get('/login', (request, response) => {

	let studentId = request.query.studentId;
	let studentPassword = request.query.studentPassword;

	loginFunctions.checkStudentIdExistsOrNot(studentId, studentPassword, response, constants);

});

app.get('/contact', (request, response) => {

	let studentId = request.query.studentId;

	contactFunctions.fetchStudentContactDetails(studentId, response, constants);

});

app.listen(constants.port, () => {
	console.log(`Server started on: http://localhost:${constants.port}`);
	console.log('\nServer Routes can be accessed from:-');
	console.log('Login Route -> http://192.168.43.100:3000/login?studentId=[]&studentPassword=[]');
	console.log('Contact Route -> http://192.168.43.100:3000/contact?studentId=[]');
});

const constants = require('./constants');
const loginFunctions = require('./loginRoute');
const contactFunctions = require('./contactRoute');
const personalFunctions = require('./personalRoute');
const passwordFunctions = require('./passwordRoute');
const downloadsFunctions = require('./downloadsRoute');
const dashboardFunctions = require('./dashboardRoute');

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

app.get('/personal', (request, response) => {

	let studentId = request.query.studentId;

	personalFunctions.fetchStudentPersonalDetails(studentId, response, constants);

});

app.post('/password', (request, response) => {

	let studentId = request.query.studentId;
	let newStudentPassword = request.query.newStudentPassword;

	passwordFunctions.updateStudentPassword(studentId, newStudentPassword, response, constants);

});

app.get('/downloads/exam', (request, response) => {
	
	let studentId = request.query.studentId;
	let semester = request.query.semester;

	downloadsFunctions.getStudentExamFiles(studentId, semester, response);

});

app.get('/downloads/fee', (request, response) => {
	
	let studentId = request.query.studentId;
	let semester = request.query.semester;

	downloadsFunctions.getStudentFeeFiles(studentId, semester, response);

});

app.get('/dashboard', (request, response) => {

	let studentId = request.query.studentId;
	let semester = request.query.semester;

	dashboardFunctions.getDashboard(studentId, semester, response, constants);

});

app.listen(constants.port, () => {

	console.log(`Server started on: http://localhost:${constants.port}`);
	console.log('\nServer Routes can be accessed from:-');
	console.log('Login Route (GET) -> http://192.168.43.100:3000/login?studentId=[]&studentPassword=[]');
	console.log('Contact Route (GET) -> http://192.168.43.100:3000/contact?studentId=[]');
	console.log('Personal Route (GET) -> http://192.168.43.100:3000/personal?studentId=[]');
	console.log('Post Password (POST) -> http://192.168.43.100:3000/password?studentId=[]&newStudentPassword=[]');
	console.log('Download Files (GET) -> http://192.168.43.100:3000/downloads/(exam||fee)?studentId=[]&semester=[]');

});

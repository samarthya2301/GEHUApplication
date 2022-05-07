function checkStudentIdExistsOrNot(studentId, studentPassword, response, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();
	const checkStudentIdExistQuery = `SELECT EXISTS(SELECT studentId FROM login_student WHERE studentId = ${studentId}) AS 'booleanStudentExists';`;
	let booleanStudentExists;

	connection.query(checkStudentIdExistQuery, (error, results, field) => {

		if (error) {

			response.status(200).json({
				'authentication': false,
				'message': 'Incorrect Student ID!'
			});
		
		} else {			
			booleanStudentExists = results[0].booleanStudentExists;
		}
		
	});

	setTimeout(() => {

		if (booleanStudentExists == 1) {
			authenticateStudent(studentId, studentPassword, response, constants);
		} else {
			response.status(200).json({
				'authentication': false,
				'message': 'Incorrect Student ID!'
			});
		}

	}, 1000);
		
}

function authenticateStudent(studentId, studentPassword, response, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();

	const loginQuery = `SELECT login_student.studentPassword FROM login_student WHERE studentId = ${studentId};`;

	connection.query(loginQuery, (error, results, field) => {

		if (error) {

			response.status(200).json({
				'authentication': false,
				'message': 'Incorrect Student ID!'
			});

		} else {
						
			const studentPasswordMd5 = constants.md5(studentPassword);
			const studentPasswordFromDatabase = results[0].studentPassword;

			if (studentPasswordMd5 == studentPasswordFromDatabase) {

				response.status(200).json({
					'authentication': true,
					'message': 'Student login details are Correct!'
				});

			} else {
				
				response.status(200).json({
					'authentication': false,
					'message': 'Incorrect Student Password!'
				});

			}

		}

	});

}

const functions = {
	'checkStudentIdExistsOrNot': checkStudentIdExistsOrNot
}

module.exports = functions;
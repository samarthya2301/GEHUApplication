function fetchStudentContactDetails(studentId, response, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();
	const fetchStudentContactQuery = `SELECT * FROM student_contact WHERE studentId = ${studentId};`;

	connection.query(fetchStudentContactQuery, (error, results, field) => {

		if (error) {

			response.status(200).json({
				'resultsAvailable': false,
				'personalEmail': '',
				'collegeEmail': '',
				'phoneOne': '',
				'phoneTwo': '',
				'studentImage': ''
			});

		} else {

			response.status(200).json({
				'resultsAvailable': true,
				'personalEmail': results[0].personalEmail,
				'collegeEmail': results[0].collegeEmail,
				'phoneOne': results[0].phoneOne,
				'phoneTwo': results[0].phoneTwo,
				'studentImage': results[0].studentImage
			});
			
		}

	});
	
}

const contactFunctions = {
	'fetchStudentContactDetails': fetchStudentContactDetails
}

module.exports = contactFunctions;
function fetchStudentPersonalDetails(studentId, response, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();
	const fetchStudentContactQuery = `SELECT * FROM student_personal WHERE studentId = ${studentId};`;

	connection.query(fetchStudentContactQuery, (error, results, field) => {

		if (error) {

			response.status(200).json({
				'resultsAvailable': false,
				'fatherName': '',
				'motherName': '',
				'dateOfBirth': '',
				'college': '',
				'course': '',
				'branch': '',
				'semester': '',
				'section': '',
				'classRollNo': '',
				'intermediatePercent': '',
				'highschoolPercent': '',
				'admissionDate': ''
			});

		} else {

			let dateOfBirth = results[0].dateOfBirth.toDateString();
			let admissionDate = results[0].admissionDate.toDateString();

			response.status(200).json({
				'resultsAvailable': true,
				'fatherName': results[0].fatherName,
				'motherName': results[0].motherName,
				'dateOfBirth': dateOfBirth,
				'college': results[0].college,
				'course': results[0].course,
				'branch': results[0].branch,
				'semester': results[0].semester,
				'section': results[0].section,
				'classRollNo': results[0].classRollNo,
				'intermediatePercent': results[0].intermediatePercent,
				'highschoolPercent': results[0].highschoolPercent,
				'admissionDate': admissionDate
			});
			
		}

	});
	
}

const personalFunctions = {
	'fetchStudentPersonalDetails': fetchStudentPersonalDetails
}

module.exports = personalFunctions;
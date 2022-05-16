function updateStudentPassword(studentId, newStudentPassword, response, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	const newStudentPasswordHash = constants.md5(newStudentPassword);

	connection.connect();
	const updateStudentPasswordQuery = `UPDATE login_student SET studentPassword = "${newStudentPasswordHash}" WHERE studentId = ${studentId};`;

	connection.query(updateStudentPasswordQuery, (error, results, field) => {

		if (error) {
			
			response.status(200).json({
				'rowFound': true,
				'passwordChanged': true
			});

			return;

		}

		let requiredRow = parseInt(results.message.substr(15, 1));

		if (requiredRow == 0) {
			response.status(200).json({
				'rowFound': false,
				'passwordChanged': false
			});
		} else {
			response.status(200).json({
				'rowFound': true,
				'passwordChanged': true
			});
		}

	});

}

const passwordFunctions = {
	'updateStudentPassword': updateStudentPassword
}

module.exports = passwordFunctions;
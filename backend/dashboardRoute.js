function getExamDashboard(studentId, semester, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();
	const fetchDashboardExamQuery = `SELECT cgpa FROM exam_table WHERE studentId = ${studentId} AND semester = ${semester};`;

	let responseJson;

	return new Promise((resolve, reject) => {

		connection.query(fetchDashboardExamQuery, (error, results, field) => {

			if (error) {

				responseJson = {
					'dashboardField': 'exam',
					'resultsAvailable': false,
					'cgpa': 0,
					'msg': 'Internal Server Error!'
				};

				reject(responseJson);

			}

			try {
				
				responseJson = {
					'dashboardField': 'exam',
					'resultsAvailable': true,
					'cgpa': results[0].cgpa,
					'msg': 'Successful!'
				};

				resolve(responseJson);

			} catch (thrownError) {

				responseJson = {
					'dashboardField': 'exam',
					'resultsAvailable': false,
					'cgpa': 0,
					'msg': 'Databse Error, Wrong Fields!'
				};

				reject(responseJson);

			}

		});

	});

}

function getFeeDashboard(studentId, semester, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();
	const fetchDashboardExamQuery = `SELECT dues, paid FROM fee_table WHERE studentId = ${studentId} AND semester = ${semester};`;

	let responseJson;

	return new Promise((resolve, reject) => {

		connection.query(fetchDashboardExamQuery, (error, results, field) => {
			
			if (error) {

				responseJson = {
					'dashboardField': 'fee',
					'resultsAvailable': false,
					'dues': 0,
					'paid': 0,
					'balance': 0,
					'msg': 'Internal Server Error!'
				};

				reject(responseJson);

			}

			try {

				const balanceAmount = results[0].dues - results[0].paid;
				
				responseJson = {
					'dashboardField': 'fee',
					'resultsAvailable': true,
					'dues': results[0].dues,
					'paid': results[0].paid,
					'balance': balanceAmount,
					'msg': 'Successful'
				};

				resolve(responseJson);

			} catch (thrownError) {

				responseJson = {
					'dashboardField': 'fee',
					'resultsAvailable': false,
					'dues': 0,
					'paid': 0,
					'balance': 0,
					'msg': 'Database Error, Wrong Fields!'
				};

				reject(responseJson);
				
			}
		
		});
	
	});

}

function getAttendanceDashboard(studentId, semester, constants) {

	const connection = constants.mysql.createConnection({
		host: 'localhost',
		user: 'root',
		password: '',
		database: 'students_gehu'
	});

	connection.connect();
	const fetchDashboardAttendanceQuery = `SELECT student_attendance.days_attended, sem_days_total.days_total FROM student_attendance NATURAL JOIN sem_days_total WHERE studentId=${studentId} AND semester=${semester};`;

	let responseJson;

	return new Promise((resolve, reject) => {
		
		connection.query(fetchDashboardAttendanceQuery, (error, results, field) => {

			if (error) {

				responseJson = {
					'dashboardField': 'attendance',
					'resultsAvailable': false,
					'daysAttended': 0,
					'daysTotal': 0,
					'attendancePercent': 0.00,
					'msg': 'Internal Server Error!'
				};

				reject(responseJson);

			}

			try {

				let attendancePercent = results[0].days_attended;

				if (results[0].days_total == 0) {
					attendancePercent = 0;
				} else {

					attendancePercent /= results[0].days_total;
					attendancePercent *= 100;
					attendancePercent = attendancePercent.toFixed(2);
					attendancePercent = parseFloat(attendancePercent);
					
				}
				
				responseJson = {
					'dashboardField': 'attendance',
					'resultsAvailable': true,
					'daysAttended': results[0].days_attended,
					'daysTotal': results[0].days_total,
					'attendancePercent': attendancePercent,
					'msg': 'Successful!'
				};

				resolve(responseJson);
				
			} catch (thrownError) {
				
				responseJson = {
					'dashboardField': 'attendance',
					'resultsAvailable': false,
					'daysAttended': 0,
					'daysTotal': 0,
					'attendancePercent': 0.00,
					'msg': 'Database Error, Wrong Fields!'
				};

				reject(responseJson);
				
			}
			
		});

	});

}

function getDashboardUtil(studentId, semester, response, constants) {

	let dashboardResponse = [];

	getExamDashboard(studentId, semester, constants).then((responseJson) => {
		dashboardResponse.push(responseJson);
	}).catch((responseJson) => {
	});

	getFeeDashboard(studentId, semester, constants).then((responseJson) => {
		dashboardResponse.push(responseJson);
	}).catch((responseJson) => {
	});

	getAttendanceDashboard(studentId, semester, constants).then((responseJson) => {
		dashboardResponse.push(responseJson);
	}).catch((responseJson) => {
	});

	setTimeout(() => {

		let finalDashboardResponse = {
			'resultsAvailable': true,
			'dashboardResponseSize': dashboardResponse.length,
			'dashboardResponse': dashboardResponse,
			'msg': 'Successful'
		};

		response.status(200).json(finalDashboardResponse);

	}, 3000);

}

function getDashboard(studentId, semester, response, constants) {
	getDashboardUtil(studentId, semester, response, constants);
}

const dashboardFunctions = {
	'getDashboard': getDashboard
}

module.exports = dashboardFunctions;
const fs = require('fs');

function getStudentExamFiles(studentId, semester, response) {

	const examFileToDownloadPath = '/downloadUtils/examResults/exam_' + studentId + '_' + semester + '.pdf';
	let downloadFileName = __dirname + examFileToDownloadPath;
	
	if (fs.existsSync(downloadFileName)) {
		response.status(200).sendFile(downloadFileName);
	} else {
		response.status(404).json({
			'fileFound': false
		});
	}
	
}

function getStudentFeeFiles(studentId, semester, response) {

	const feeFileToDownloadPath = '/downloadUtils/feeReceipts/fee_' + studentId + '_' + semester + '.pdf';
	let downloadFileName = __dirname + feeFileToDownloadPath;

	if (fs.existsSync(downloadFileName)) {
		response.status(200).sendFile(downloadFileName);
	} else {
		response.status(404).json({
			'fileFound': false
		});
	}
		
}

const downloadsFunctions = {
	'getStudentExamFiles': getStudentExamFiles,
	'getStudentFeeFiles': getStudentFeeFiles
}

module.exports = downloadsFunctions;
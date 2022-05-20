function getStudentExamFiles(studentId, semester, response) {

	const examFileToDownloadPath = '/downloadUtils/examResults/exam_' + studentId + '_' + semester + '.pdf';
	response.sendFile(__dirname + examFileToDownloadPath);

}

function getStudentFeeFiles(studentId, semester, response) {

	const feeFileToDownloadPath = '/downloadUtils/feeReceipts/fee_' + studentId + '_' + semester + '.pdf';
	response.sendFile(__dirname + feeFileToDownloadPath);

}

const downloadsFunctions = {
	'getStudentExamFiles': getStudentExamFiles,
	'getStudentFeeFiles': getStudentFeeFiles
}

module.exports = downloadsFunctions;
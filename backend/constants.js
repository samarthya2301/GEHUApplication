const express = require('express');
const mysql = require('mysql');
const md5 = require('md5');
const port = 3000;

const constants = {
	'express': express,
	'mysql': mysql,
	'port': port,
	'md5': md5
};

module.exports = constants;
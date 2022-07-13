-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2022 at 12:40 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `students_gehu`
--

-- --------------------------------------------------------

--
-- Table structure for table `exam_table`
--

CREATE TABLE `exam_table` (
  `studentId` int(5) NOT NULL,
  `semester` int(1) NOT NULL,
  `sgpa` decimal(3,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `exam_table`
--

INSERT INTO `exam_table` (`studentId`, `semester`, `sgpa`) VALUES
(10005, 1, '8.25'),
(10005, 2, '7.22'),
(10005, 3, '9.23'),
(10005, 4, '9.11'),
(10005, 5, '8.89'),
(10005, 6, '7.89'),
(10005, 7, '0.00'),
(10005, 8, '0.00');

-- --------------------------------------------------------

--
-- Table structure for table `fee_table`
--

CREATE TABLE `fee_table` (
  `studentId` int(5) NOT NULL,
  `semester` int(1) NOT NULL,
  `dues` int(6) NOT NULL,
  `paid` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fee_table`
--

INSERT INTO `fee_table` (`studentId`, `semester`, `dues`, `paid`) VALUES
(10005, 1, 56000, 56000),
(10005, 2, 58000, 58000),
(10005, 3, 48000, 48000),
(10005, 4, 59456, 59456),
(10005, 5, 61235, 60000),
(10005, 6, 62114, 61000),
(10005, 7, 58526, 0),
(10005, 7, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `login_student`
--

CREATE TABLE `login_student` (
  `studentId` int(5) NOT NULL,
  `studentPassword` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login_student`
--

INSERT INTO `login_student` (`studentId`, `studentPassword`) VALUES
(10005, 'd16fb36f0911f878998c136191af705e');

-- --------------------------------------------------------

--
-- Table structure for table `sem_days_total`
--

CREATE TABLE `sem_days_total` (
  `semester` int(1) NOT NULL,
  `days_total` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sem_days_total`
--

INSERT INTO `sem_days_total` (`semester`, `days_total`) VALUES
(1, 152),
(2, 171),
(3, 150),
(4, 168),
(5, 144),
(6, 169),
(7, 0),
(8, 0);

-- --------------------------------------------------------

--
-- Table structure for table `student_attendance`
--

CREATE TABLE `student_attendance` (
  `studentId` int(5) NOT NULL,
  `semester` int(1) NOT NULL,
  `days_attended` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_attendance`
--

INSERT INTO `student_attendance` (`studentId`, `semester`, `days_attended`) VALUES
(10005, 1, 99),
(10005, 2, 169),
(10005, 3, 123),
(10005, 4, 152),
(10005, 5, 142),
(10005, 6, 114),
(10005, 7, 0),
(10005, 8, 0);

-- --------------------------------------------------------

--
-- Table structure for table `student_contact`
--

CREATE TABLE `student_contact` (
  `studentId` int(5) NOT NULL,
  `personalEmail` varchar(50) NOT NULL,
  `collegeEmail` varchar(50) NOT NULL,
  `phoneOne` varchar(10) NOT NULL,
  `phoneTwo` varchar(10) NOT NULL,
  `studentImage` varchar(150) NOT NULL,
  `studentName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_contact`
--

INSERT INTO `student_contact` (`studentId`, `personalEmail`, `collegeEmail`, `phoneOne`, `phoneTwo`, `studentImage`, `studentName`) VALUES
(10005, 'rohan.sharma@gmail.com', 'rohansharma.me10005@gehu.ac.in', '9876543210', '', 'https://i.postimg.cc/4d2SVFFR/rohan.jpg', 'Rohan Sharma');

-- --------------------------------------------------------

--
-- Table structure for table `student_personal`
--

CREATE TABLE `student_personal` (
  `studentId` int(5) NOT NULL,
  `fatherName` varchar(40) NOT NULL,
  `motherName` varchar(40) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `college` varchar(40) NOT NULL,
  `course` varchar(40) NOT NULL,
  `branch` varchar(40) NOT NULL,
  `semester` int(1) NOT NULL,
  `section` varchar(1) NOT NULL,
  `classRollNo` int(2) NOT NULL,
  `intermediatePercent` decimal(5,2) NOT NULL,
  `highschoolPercent` double(5,2) NOT NULL,
  `admissionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_personal`
--

INSERT INTO `student_personal` (`studentId`, `fatherName`, `motherName`, `dateOfBirth`, `college`, `course`, `branch`, `semester`, `section`, `classRollNo`, `intermediatePercent`, `highschoolPercent`, `admissionDate`) VALUES
(10005, 'Kapil Sharma', 'Amrita Sharma', '2000-02-12', 'GEHU Dehradun', 'Bachelor of Technology', 'Mechanical Engineering', 6, 'C', 68, '89.25', 93.64, '2019-07-15');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `exam_table`
--
ALTER TABLE `exam_table`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `fee_table`
--
ALTER TABLE `fee_table`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `login_student`
--
ALTER TABLE `login_student`
  ADD PRIMARY KEY (`studentId`);

--
-- Indexes for table `student_attendance`
--
ALTER TABLE `student_attendance`
  ADD KEY `studentId` (`studentId`);

--
-- Indexes for table `student_contact`
--
ALTER TABLE `student_contact`
  ADD PRIMARY KEY (`studentId`);

--
-- Indexes for table `student_personal`
--
ALTER TABLE `student_personal`
  ADD PRIMARY KEY (`studentId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `exam_table`
--
ALTER TABLE `exam_table`
  ADD CONSTRAINT `exam_table_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal` (`studentId`);

--
-- Constraints for table `fee_table`
--
ALTER TABLE `fee_table`
  ADD CONSTRAINT `fee_table_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal` (`studentId`);

--
-- Constraints for table `student_attendance`
--
ALTER TABLE `student_attendance`
  ADD CONSTRAINT `student_attendance_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student_personal` (`studentId`);

--
-- Constraints for table `student_contact`
--
ALTER TABLE `student_contact`
  ADD CONSTRAINT `student_contact_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `login_student` (`studentId`);

--
-- Constraints for table `student_personal`
--
ALTER TABLE `student_personal`
  ADD CONSTRAINT `student_personal_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `login_student` (`studentId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

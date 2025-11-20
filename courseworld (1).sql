-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2025 at 10:05 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `courseworld`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `course_id` bigint(20) NOT NULL,
  `language` varchar(255) NOT NULL,
  `level` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `syllabus_pdf` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `language`, `level`, `created_at`, `deleted_at`, `description`, `img`, `name`, `price`, `syllabus_pdf`, `updated_at`, `category_id`, `user_id`) VALUES
(12, 'English', 'Intermediate', '2025-03-29 07:46:29.000000', NULL, 'The Java Backend Development course is a comprehensive program designed to equip learners with the skills and knowledge required to build robust, secure, and scalable backend systems using Java. Java, one of the most versatile and widely-used programming languages, powers the backend of countless enterprise systems, web applications, and large-scale platforms. This course covers everything from Java fundamentals to advanced backend development concepts, enabling you to create efficient server-side applications.\r\n\r\nWhether you\'re a beginner aiming to start your journey in backend development or an experienced developer looking to sharpen your skills, this course provides a structured roadmap to mastering Java for backend development. By the end of this course, you\'ll be able to design and implement complex backend systems that meet the demands of modern applications.', '1743788586974_java.webp', 'java backend development', 20000, '1743234748605_ws-commands.pdf', '2025-04-04 17:43:46.000000', 1, 4),
(13, 'Hindi', 'Beginner', '2025-03-29 21:04:36.000000', NULL, 'The Backend Domination: Create Efficient Backends Using Node.js course is designed to empower developers with the skills and knowledge required to build scalable, efficient, and maintainable backend systems. Node.js, a powerful runtime environment, is at the heart of this course, enabling you to leverage JavaScript for server-side development. Whether you\'re a beginner or an intermediate developer, this course offers comprehensive insights into mastering backend development with Node.js.\r\n\r\nThrough hands-on projects, real-world examples, and structured lessons, you will learn to create robust APIs, integrate databases, handle asynchronous operations, and optimize performance. By the end of this course, you\'ll be equipped to dominate backend development and create applications that are both efficient and scalable.', '1743788519684_backend_nodejs.jpeg', 'Backend Domination Create Efficient Backend Using node js', 10000, '1743282276810_wireshark commands.pdf', '2025-04-04 17:41:59.000000', 1, 4),
(16, 'English', 'Beginner', '2025-04-04 02:44:34.000000', NULL, 'The Aptitude and Reasoning for Campus Placement course is meticulously designed to help students excel in the aptitude and reasoning sections of campus recruitment tests. These sections are often the first hurdle in placement processes, and mastering them can significantly boost your chances of securing your dream job. This course provides a comprehensive understanding of quantitative aptitude, logical reasoning, and analytical problem-solving techniques, ensuring you are well-prepared to tackle placement exams with confidence.\r\n\r\nThe course adopts a structured approach, starting with foundational concepts and gradually progressing to advanced problem-solving strategies. Whether you are preparing for IT placements, core engineering companies, or government recruitment exams, this course equips you with the skills needed to succeed.', '1745720149402_campus_placement.webp', 'Aptitute And Reasoning For Campus Placement', 2000, '1743734674330_tracert.pdf', '2025-04-27 02:15:49.000000', 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `course_category`
--

CREATE TABLE `course_category` (
  `category_id` bigint(20) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_category`
--

INSERT INTO `course_category` (`category_id`, `category_name`, `created_at`, `deleted_at`, `updated_at`) VALUES
(1, 'Web Development', '2025-03-22 07:29:00.000000', NULL, '2025-04-27 17:26:11.000000'),
(2, 'App Development', '2025-03-22 07:29:09.000000', NULL, '2025-03-22 07:29:09.000000'),
(3, 'Business Analysis', '2025-03-22 07:29:45.000000', NULL, '2025-03-22 07:29:45.000000'),
(4, 'dsa123', '2025-04-27 18:41:21.000000', NULL, '2025-04-27 18:41:38.000000');

-- --------------------------------------------------------

--
-- Table structure for table `course_video`
--

CREATE TABLE `course_video` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_video`
--

INSERT INTO `course_video` (`id`, `created_at`, `deleted_at`, `file_name`, `updated_at`, `video_name`, `course_id`) VALUES
(18, '2025-04-04 17:56:35.000000', NULL, '1743789394295_yt1z.net - Master JavaScript Essentials for Backend Development Part 1 (2160p60).mp4', '2025-04-04 17:56:35.000000', 'backend video 1', 13),
(19, '2025-04-04 17:59:02.000000', NULL, '1743789542729_yt1z.net - Install Node.js Learn File System Operations Backend Development Part 2 (1080p60).mp4', '2025-04-04 17:59:02.000000', 'backend video 2', 13),
(20, '2025-04-04 17:59:36.000000', NULL, '1743789576639_yt1z.net - NPM Basics Advanced Features Backend Development Part 3 (1080p60).mp4', '2025-04-04 17:59:36.000000', 'backend video 3', 13),
(22, '2025-04-04 18:22:26.000000', NULL, '1743790944472_yt1z.net - Master the Basics DSA with JavaScript Part 1 (1440p60).mp4', '2025-04-04 18:22:26.000000', 'DSA Part 1', 16),
(23, '2025-04-04 18:23:04.000000', NULL, '1743790983527_yt1z.net - Understanding Conditionals DSA with JavaScript Part 2 (1440p).mp4', '2025-04-04 18:23:04.000000', 'DSA Part 2', 16),
(24, '2025-04-04 18:23:58.000000', NULL, '1743791036280_yt1z.net - Everything About Loops DSA with JavaScript Part 3 (1440p).mp4', '2025-04-04 18:23:58.000000', 'DSA Part 3', 16),
(25, '2025-04-04 18:30:14.000000', NULL, '1743791414333_yt1z.net - Java for Complete Beginners Programming Fundamentals _ Part 1 (1080p60).mp4', '2025-04-04 18:30:14.000000', 'part 1', 12),
(26, '2025-04-04 18:31:58.000000', NULL, '1743791517772_yt1z.net - Java for Complete Beginners Programming Fundamentals _ Part 2 (1080p60).mp4', '2025-04-04 18:31:58.000000', 'part 2', 12);

-- --------------------------------------------------------

--
-- Table structure for table `enrollment`
--

CREATE TABLE `enrollment` (
  `id` bigint(20) NOT NULL,
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollment`
--

INSERT INTO `enrollment` (`id`, `course_id`, `user_id`) VALUES
(5, 12, 3);

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_token`
--

CREATE TABLE `password_reset_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `course_id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `currency` varchar(255) NOT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `razorpay_order_id` varchar(255) NOT NULL,
  `razorpay_payment_id` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `amount`, `course_id`, `created_at`, `currency`, `payment_method`, `razorpay_order_id`, `razorpay_payment_id`, `status`, `updated_at`, `user_id`) VALUES
(1, 50000, 12, '2025-03-31 01:59:43.000000', 'INR', 'wallet', 'order_QDQGm10CrCvQYU', 'pay_QDQGqtKnK3wy2N', 'Success', '2025-03-31 01:59:43.000000', 0),
(2, 50000, 12, '2025-03-31 02:02:43.000000', 'INR', 'wallet', 'order_QDQJplFs4vJigU', 'pay_QDQJvelAWklfAs', 'Success', '2025-03-31 02:02:43.000000', 3),
(3, 50000, 12, '2025-03-31 02:57:01.000000', 'INR', 'wallet', 'order_QDRFED4AFXENMU', 'pay_QDRFLDuXlxZ7mJ', 'Success', '2025-03-31 02:57:01.000000', 3),
(4, 50000, 12, '2025-03-31 03:01:52.000000', 'INR', 'wallet', 'order_QDRKMwCxnfiWnF', 'pay_QDRKT6qyhwU9v9', 'Success', '2025-03-31 03:01:52.000000', 3),
(5, 2000, 16, '2025-04-04 20:55:35.000000', 'INR', 'wallet', 'order_QFJkrvQwQ2s2nP', 'pay_QFJl7iQ70e8KXP', 'Success', '2025-04-04 20:55:35.000000', 3);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` bigint(20) NOT NULL,
  `admin_verified` bit(1) NOT NULL,
  `role` tinyint(4) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `name`, `email`, `password`, `address`, `phone`, `admin_verified`, `role`, `enabled`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'admin', 'admin@gmail.com', '$2a$10$znE1Uzhubbf183EF81bQLugVtm1kEHmKPUKlRVr4htDOcp2W56KrO', 'admin home address', 1234567890, b'1', 2, b'1', '2025-03-04 03:23:47.000000', '2025-03-26 03:23:47.000000', NULL),
(3, 'vinay', 'rathodvinay020@gmail.com', '$2a$10$ac3Y6YUE/eIArkMci7V8IOl1mciGT2UlXywUO3bCYCKHQLauFH.yS', NULL, 1234567897, b'1', 0, b'1', '2025-03-22 06:22:29.000000', NULL, NULL),
(4, 'Vinay Rathod', 'vinayrathod7967747@gmail.com', '$2a$10$dfahvJSfEe6RcxwtAuLWHe/Plj7USds9A20ggI0m0u7Xs7B6mN5H6', NULL, 9016422877, b'1', 1, b'1', '2025-03-23 00:37:43.000000', NULL, NULL),
(11, 'bhumika', 'chavdabhumika615@gmail.com', '$2a$10$xgh5PYP9gtWv2XgKaai2o.ZGnmBRF.QdKA1lQ.e7YBdEzBoS6MsWO', NULL, 9016422877, b'1', 1, b'1', '2025-04-27 18:47:04.000000', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `verification_tokens`
--

CREATE TABLE `verification_tokens` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`),
  ADD KEY `FKbnefdeo2rp4e3aflyp2rsw1nb` (`category_id`),
  ADD KEY `FKo3767wbj6ow5axv38qej0gxo9` (`user_id`);

--
-- Indexes for table `course_category`
--
ALTER TABLE `course_category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `course_video`
--
ALTER TABLE `course_video`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqy33it41wsc2klnhl0xn8aeae` (`course_id`);

--
-- Indexes for table `enrollment`
--
ALTER TABLE `enrollment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5lwtbncug84d4ero33v3cfxvl` (`user_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `verification_tokens`
--
ALTER TABLE `verification_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKdqp95ggn6gvm865km5muba2o5` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `course_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `course_category`
--
ALTER TABLE `course_category`
  MODIFY `category_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `course_video`
--
ALTER TABLE `course_video`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `enrollment`
--
ALTER TABLE `enrollment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `verification_tokens`
--
ALTER TABLE `verification_tokens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `FKbnefdeo2rp4e3aflyp2rsw1nb` FOREIGN KEY (`category_id`) REFERENCES `course_category` (`category_id`),
  ADD CONSTRAINT `FKo3767wbj6ow5axv38qej0gxo9` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `course_video`
--
ALTER TABLE `course_video`
  ADD CONSTRAINT `FKqy33it41wsc2klnhl0xn8aeae` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`);

--
-- Constraints for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD CONSTRAINT `FK5lwtbncug84d4ero33v3cfxvl` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `verification_tokens`
--
ALTER TABLE `verification_tokens`
  ADD CONSTRAINT `FKnoled469hi2uuqjqfsegbuxp5` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

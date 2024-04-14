DROP DATABASE IF EXISTS `api-portal`;
CREATE DATABASE `api-portal`;
Use `api-portal`;

CREATE TABLE `api-portal`.`roles` (
  `role` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`role`),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE);

CREATE TABLE `api-portal`.`authorities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  `authority` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`role`) REFERENCES roles(role));
  
CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fname` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `role_exp_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  FOREIGN KEY (`role`) REFERENCES roles(role)
);

CREATE TABLE  `api-portal`.`user_activities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `level` VARCHAR(25) NOT NULL,
  `action` VARCHAR(25) NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES users(id)
);

DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserAuthorities`(IN userEmail VARCHAR(255))
BEGIN
    DECLARE userRole VARCHAR(255);
    DECLARE userRoleExpDate DATETIME(6);
  
    SELECT role, role_exp_date INTO userRole, userRoleExpDate FROM users WHERE email = userEmail;

    IF userRole = 'premium' AND userRoleExpDate < NOW() THEN
        UPDATE users SET role_exp_date = NULL, role = 'standard' WHERE email = userEmail;
        SET userRole = 'standard';
    END IF;

    SELECT authority FROM `authorities` WHERE `role` = userRole;
END$$

DELIMITER ;


Insert into `roles` (`role`, `name`) VALUES ('admin', 'admin');
Insert into `roles` (`role`, `name`) VALUES ('standard', 'standard');
Insert into `roles` (`role`, `name`) VALUES ('premium', 'premium');
Insert into `roles` (`role`, `name`) VALUES ('public', 'public');

Insert into `authorities` (`role`, `authority`) VALUES ('admin', 'handle_projects');
Insert into `authorities` (`role`, `authority`) VALUES ('standard', 'handle_projects');
Insert into `authorities` (`role`, `authority`) VALUES ('premium', 'handle_projects');

Insert into `authorities` (`role`, `authority`) VALUES ('admin', 'handle_qualifications');
Insert into `authorities` (`role`, `authority`) VALUES ('standard', 'handle_qualifications');
Insert into `authorities` (`role`, `authority`) VALUES ('premium', 'handle_qualifications');

Insert into `authorities` (`role`, `authority`) VALUES ('public', 'view_projects');
Insert into `authorities` (`role`, `authority`) VALUES ('public', 'view_qualifications');

Insert into `users` (`created_at`, `email`, `fname`, `lname`, `password`, `role`, `status`, `role_exp_date` ) VALUES (NOW(), 'shenalakalanka513@gmail.com', 'Shenal', 'Akalanka', '$2a$12$HqOxY5e1J6gB140dje83gu82rEVyy/5ofwOcb5SpzDbTc/3SCYNf6', 'admin', 'approved', NULL);
Insert into `users` (`created_at`, `email`, `fname`, `lname`, `password`, `role`, `status`, `role_exp_date` ) VALUES (NOW(), 'standard@gmail.com', 'Test', 'User', '$2a$12$HqOxY5e1J6gB140dje83gu82rEVyy/5ofwOcb5SpzDbTc/3SCYNf6', 'standard', 'approved', NULL);
Insert into `users` (`created_at`, `email`, `fname`, `lname`, `password`, `role`, `status`, `role_exp_date` ) VALUES (NOW(), 'premium@gmail.com', 'Shenal', 'Akalanka', '$2a$12$HqOxY5e1J6gB140dje83gu82rEVyy/5ofwOcb5SpzDbTc/3SCYNf6', 'premium', 'approved', NOW());

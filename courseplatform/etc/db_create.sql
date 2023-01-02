-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema courseplatform
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `courseplatform` ;

-- -----------------------------------------------------
-- Schema courseplatform
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `courseplatform` DEFAULT CHARACTER SET utf8 ;
USE `courseplatform` ;

-- -----------------------------------------------------
-- Table `courseplatform`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`user` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('STUDENT', 'TEACHER', 'ADMIN') NOT NULL,
  `registration_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`student` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `blocked` TINYINT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `courseplatform`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`admin` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_admin_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_admin_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `courseplatform`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`teacher` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`teacher` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_teacher_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `courseplatform`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`course` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NULL,
  `duration` INT NOT NULL,
  `min_grade` INT NULL,
  `max_grade` INT NULL,
  `starting_date` DATETIME NOT NULL,
  `status` ENUM('CREATED', 'MODIFYING', 'ENROLLING', 'IN_PROGRESS', 'FINISHED') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`theme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`theme` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`theme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`courses_themes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`courses_themes` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`courses_themes` (
  `course_id` INT NOT NULL,
  `theme_id` INT NOT NULL,
  PRIMARY KEY (`course_id`, `theme_id`),
  INDEX `fk_course_has_theme_theme1_idx` (`theme_id` ASC) VISIBLE,
  INDEX `fk_course_has_theme_course1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_has_theme_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `courseplatform`.`course` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_course_has_theme_theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `courseplatform`.`theme` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`unit` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`unit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_unit_course1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_unit_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `courseplatform`.`course` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`module`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`module` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`module` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `unit_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_module_unit1_idx` (`unit_id` ASC) VISIBLE,
  CONSTRAINT `fk_module_unit1`
    FOREIGN KEY (`unit_id`)
    REFERENCES `courseplatform`.`unit` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`material` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`material` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1000) NULL,
  `module_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_material_module1_idx` (`module_id` ASC) VISIBLE,
  CONSTRAINT `fk_material_module1`
    FOREIGN KEY (`module_id`)
    REFERENCES `courseplatform`.`module` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`video_lecture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`video_lecture` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`video_lecture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `video_url` VARCHAR(300) NOT NULL,
  `material_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_video_lecture_material1_idx` (`material_id` ASC) VISIBLE,
  CONSTRAINT `fk_video_lecture_material1`
    FOREIGN KEY (`material_id`)
    REFERENCES `courseplatform`.`material` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`text_lecture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`text_lecture` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`text_lecture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `file_url` VARCHAR(300) NULL,
  `material_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_text_lecture_material1_idx` (`material_id` ASC) VISIBLE,
  CONSTRAINT `fk_text_lecture_material1`
    FOREIGN KEY (`material_id`)
    REFERENCES `courseplatform`.`material` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`test` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`test` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `max_grade` INT NOT NULL,
  `material_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_test_material1_idx` (`material_id` ASC) VISIBLE,
  CONSTRAINT `fk_test_material1`
    FOREIGN KEY (`material_id`)
    REFERENCES `courseplatform`.`material` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`assignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`assignment` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`assignment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(1000) NOT NULL,
  `max_grade` INT NOT NULL,
  `material_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assignment_material1_idx` (`material_id` ASC) VISIBLE,
  CONSTRAINT `fk_assignment_material1`
    FOREIGN KEY (`material_id`)
    REFERENCES `courseplatform`.`material` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`question` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(100) NOT NULL,
  `grade` INT NOT NULL,
  `test_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_question_test1_idx` (`test_id` ASC) VISIBLE,
  CONSTRAINT `fk_question_test1`
    FOREIGN KEY (`test_id`)
    REFERENCES `courseplatform`.`test` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courseplatform`.`answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `courseplatform`.`answer` ;

CREATE TABLE IF NOT EXISTS `courseplatform`.`answer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `option` VARCHAR(45) NOT NULL,
  `correct` TINYINT NOT NULL,
  `question_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_answer_question1_idx` (`question_id` ASC) VISIBLE,
  CONSTRAINT `fk_answer_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `courseplatform`.`question` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

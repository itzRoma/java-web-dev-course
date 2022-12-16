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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

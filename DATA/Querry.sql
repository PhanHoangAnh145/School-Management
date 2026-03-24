-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema school_management
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema school_management
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `school_management` DEFAULT CHARACTER SET utf8 ;
USE `school_management` ;

-- -----------------------------------------------------
-- Table `school_management`.`school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`school` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `grade` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`class` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `school_id` INT NOT NULL,
  `grade` VARCHAR(45) NULL,
  `year` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Class_School_idx` (`school_id` ASC) VISIBLE,
  CONSTRAINT `fk_Class_School`
    FOREIGN KEY (`school_id`)
    REFERENCES `school_management`.`school` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`employee` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `school_id` INT NOT NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_school1_idx` (`school_id` ASC) VISIBLE,
  CONSTRAINT `fk_employee_school1`
    FOREIGN KEY (`school_id`)
    REFERENCES `school_management`.`school` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`teacher` (
  `id` INT NOT NULL,
  `Employee_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `date_of_birth` VARCHAR(45) NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_teacher_employee1_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `school_management`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`student` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `date_of_birth` VARCHAR(45) NULL,
  `class_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_class1_idx` (`class_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `school_management`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`subject` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`subject_teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`subject_teacher` (
  `subject_id` INT NOT NULL,
  `teacher_id` INT NOT NULL,
  PRIMARY KEY (`subject_id`, `teacher_id`),
  INDEX `fk_subject_has_teacher_teacher1_idx` (`teacher_id` ASC) VISIBLE,
  INDEX `fk_subject_has_teacher_subject1_idx` (`subject_id` ASC) VISIBLE,
  CONSTRAINT `fk_subject_has_teacher_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school_management`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subject_has_teacher_teacher1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `school_management`.`teacher` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`grade` (
  `student_id` INT NOT NULL,
  `id` VARCHAR(45) NOT NULL,
  INDEX `fk_student_transcript_student1_idx` (`student_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_student_transcript_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `school_management`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`student_transcript`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`student_transcript` (
  `subject_id` INT NOT NULL,
  `mark` DOUBLE NULL,
  `student_transcript_id` VARCHAR(45) NOT NULL,
  `id` VARCHAR(45) NOT NULL,
  INDEX `fk_student_has_subject_subject1_idx` (`subject_id` ASC) VISIBLE,
  INDEX `fk_grade_student_transcript1_idx` (`student_transcript_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_student_has_subject_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school_management`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_student_transcript1`
    FOREIGN KEY (`student_transcript_id`)
    REFERENCES `school_management`.`grade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`class_logbook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`class_logbook` (
  `id` INT NOT NULL,
  `class_id` INT NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_class_logbook_class2_idx` (`class_id` ASC) VISIBLE,
  CONSTRAINT `fk_class_logbook_class2`
    FOREIGN KEY (`class_id`)
    REFERENCES `school_management`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`class_logbook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`class_logbook` (
  `id` INT NOT NULL,
  `class_id` INT NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_class_logbook_class2_idx` (`class_id` ASC) VISIBLE,
  CONSTRAINT `fk_class_logbook_class2`
    FOREIGN KEY (`class_id`)
    REFERENCES `school_management`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`teacher_class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`teacher_class` (
  `teacher_id` INT NOT NULL,
  `class_id` INT NOT NULL,
  PRIMARY KEY (`teacher_id`, `class_id`),
  INDEX `fk_teacher_has_class_class1_idx` (`class_id` ASC) VISIBLE,
  INDEX `fk_teacher_has_class_teacher1_idx` (`teacher_id` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_has_class_teacher1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `school_management`.`teacher` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_has_class_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `school_management`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`student_record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`student_record` (
  `id` INT NOT NULL,
  `description` VARCHAR(45) NULL,
  `student_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_record_student1_idx` (`student_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_record_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `school_management`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`student_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`student_detail` (
  `id` INT NOT NULL,
  `full_name` VARCHAR(45) NULL,
  `avatar` BLOB NULL,
  `address` VARCHAR(45) NULL,
  `hobby` VARCHAR(45) NULL,
  `student_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_detail_student1_idx` (`student_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_detail_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `school_management`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`parent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`parent` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `addresss` VARCHAR(45) NULL,
  `student_id` INT NOT NULL,
  `phone_number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_parent_student1_idx` (`student_id` ASC) VISIBLE,
  CONSTRAINT `fk_parent_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `school_management`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`employee_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`employee_detail` (
  `id` INT NOT NULL,
  `day_of_birth` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_detail_employee1_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_employee_detail_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `school_management`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`subject` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`teacher_subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`teacher_subject` (
  `teacher_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`teacher_id`, `subject_id`),
  INDEX `fk_teacher_has_subject_subject1_idx` (`subject_id` ASC) VISIBLE,
  INDEX `fk_teacher_has_subject_teacher1_idx` (`teacher_id` ASC) VISIBLE,
  CONSTRAINT `fk_teacher_has_subject_teacher1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `school_management`.`teacher` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_has_subject_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school_management`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`student_subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`student_subject` (
  `student_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`student_id`, `subject_id`),
  INDEX `fk_student_has_subject_subject2_idx` (`subject_id` ASC) VISIBLE,
  INDEX `fk_student_has_subject_student1_idx` (`student_id` ASC) VISIBLE,
  CONSTRAINT `fk_student_has_subject_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `school_management`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_has_subject_subject2`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school_management`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`transcription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`transcription` (
  `id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `year` VARCHAR(45) NULL,
  `rate` VARCHAR(45) NULL,
  `mark` DOUBLE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transcription_student1_idx` (`student_id` ASC) VISIBLE,
  CONSTRAINT `fk_transcription_student1`
    FOREIGN KEY (`student_id`)
    REFERENCES `school_management`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_management`.`grade_report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_management`.`grade_report` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `10_mark` DOUBLE NULL,
  `transcription_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_grade_report_transcription1_idx` (`transcription_id` ASC) VISIBLE,
  CONSTRAINT `fk_grade_report_transcription1`
    FOREIGN KEY (`transcription_id`)
    REFERENCES `school_management`.`transcription` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

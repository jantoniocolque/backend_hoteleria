-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema nidodb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema nidodb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nidodb` DEFAULT CHARACTER SET utf8 ;
USE `nidodb` ;

-- -----------------------------------------------------
-- Table `nidodb`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`locations` (
  `location_id` BIGINT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `latitude` DECIMAL(8,6) NULL,
  `longitude` DECIMAL(8,6) NULL,
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`categories` (
  `category_id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(80) NOT NULL,
  `description` VARCHAR(180) NULL,
  `url_image` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`products` (
  `product_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(180) NULL,
  `availability` TINYINT NULL,
  `score` INT NULL,
  `locations_location_id` BIGINT NOT NULL,
  `categories_category_id` BIGINT NOT NULL,
  PRIMARY KEY (`product_id`),
  INDEX `fk_products_locations1_idx` (`locations_location_id` ASC) VISIBLE,
  INDEX `fk_products_categories1_idx` (`categories_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_products_locations1`
    FOREIGN KEY (`locations_location_id`)
    REFERENCES `nidodb`.`locations` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_categories1`
    FOREIGN KEY (`categories_category_id`)
    REFERENCES `nidodb`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`images` (
  `image_id` BIGINT NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(150) NOT NULL,
  `title` VARCHAR(80) NULL,
  `products_product_id` BIGINT NOT NULL,
  PRIMARY KEY (`image_id`),
  INDEX `fk_images_products1_idx` (`products_product_id` ASC) VISIBLE,
  CONSTRAINT `fk_images_products1`
    FOREIGN KEY (`products_product_id`)
    REFERENCES `nidodb`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`users` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `phone` VARCHAR(40) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`reserves`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`reserves` (
  `reservation_id` BIGINT NOT NULL AUTO_INCREMENT,
  `date_in` DATE NOT NULL,
  `date_out` DATE NOT NULL,
  `users_user_id` BIGINT NOT NULL,
  `products_product_id` BIGINT NOT NULL,
  PRIMARY KEY (`reservation_id`),
  INDEX `fk_reserves_users1_idx` (`users_user_id` ASC) VISIBLE,
  INDEX `fk_reserves_products1_idx` (`products_product_id` ASC) VISIBLE,
  CONSTRAINT `fk_reserves_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `nidodb`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reserves_products1`
    FOREIGN KEY (`products_product_id`)
    REFERENCES `nidodb`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`features` (
  `feature_id` INT NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `icon` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`feature_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nidodb`.`products_has_features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nidodb`.`products_has_features` (
  `products_product_id` BIGINT NOT NULL,
  `features_feature_id` INT NOT NULL,
  PRIMARY KEY (`products_product_id`, `features_feature_id`),
  INDEX `fk_products_has_features_features1_idx` (`features_feature_id` ASC) VISIBLE,
  INDEX `fk_products_has_features_products1_idx` (`products_product_id` ASC) VISIBLE,
  CONSTRAINT `fk_products_has_features_products1`
    FOREIGN KEY (`products_product_id`)
    REFERENCES `nidodb`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_has_features_features1`
    FOREIGN KEY (`features_feature_id`)
    REFERENCES `nidodb`.`features` (`feature_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

CREATE TABLE `doctors`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL
);
CREATE TABLE `specialties`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);
CREATE TABLE `doctors_specialties`(
    `doctor_id` BIGINT UNSIGNED NOT NULL,
    `specialty_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(`doctor_id`, `specialty_id`)
);
CREATE TABLE `doctor_records`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `doctor_id` BIGINT NOT NULL,
    `status_name` VARCHAR(255) NOT NULL,
    `updated` DATETIME NOT NULL,
    UNIQUE(`doctor_id`, `status_name`), /*constrains combinations of doctor_id and status_name for each doc to have only one status*/
    INDEX(`status_name`)
);
CREATE TABLE `doctor_status`(
    `status` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`status`)
);
CREATE TABLE `doctors_records`(
    `doctor_id` BIGINT UNSIGNED NOT NULL,
    `record_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(`doctor_id`, `record_id`)
);
CREATE TABLE `doctors_clinics`(
    `doctor_id` BIGINT UNSIGNED NOT NULL,
    `clinic_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(`doctor_id`, `clinic_id`)
);
CREATE TABLE `clinics`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `address` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `city_id` BIGINT UNSIGNED NOT NULL,
    `document_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `cities`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `country_name` VARCHAR(255) NOT NULL
);
CREATE TABLE `countries`(
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`name`)
);
CREATE TABLE `documents`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type_name` VARCHAR(255) NOT NULL,
    `content` BLOB NOT NULL
);
CREATE TABLE `document_types`(
    `type` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`type`)
);
CREATE TABLE `clinic_records`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `clinic_id` BIGINT UNSIGNED NOT NULL,
    `updated` DATETIME NOT NULL,
    `status_name` VARCHAR(255) NOT NULL
);
CREATE TABLE `clinic_status`(
    `status` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`status`)
);

ALTER TABLE
    `doctors_specialties` ADD CONSTRAINT FOREIGN KEY(`specialty_id`) REFERENCES `specialties`(`id`);
ALTER TABLE
    `clinic_records` ADD CONSTRAINT FOREIGN KEY(`status_name`) REFERENCES `clinic_status`(`status`);
ALTER TABLE
    `doctors` ADD CONSTRAINT FOREIGN KEY(`id`) REFERENCES `doctors_clinics`(`doctor_id`);
ALTER TABLE
    `clinics` ADD CONSTRAINT FOREIGN KEY(`city_id`) REFERENCES `cities`(`id`);
ALTER TABLE
    `doctor_status` ADD CONSTRAINT FOREIGN KEY(`status`) REFERENCES `doctor_records`(`status_name`);
ALTER TABLE
    `cities` ADD CONSTRAINT FOREIGN KEY(`country_name`) REFERENCES `countries`(`name`);
ALTER TABLE
    `doctors_specialties` ADD CONSTRAINT FOREIGN KEY(`specialty_id`) REFERENCES `doctors`(`id`);
ALTER TABLE
    `doctors_clinics` ADD CONSTRAINT FOREIGN KEY(`clinic_id`) REFERENCES `clinics`(`id`);
ALTER TABLE
    `clinics` ADD CONSTRAINT FOREIGN KEY(`document_id`) REFERENCES `documents`(`id`);
ALTER TABLE
    `doctors_records` ADD CONSTRAINT FOREIGN KEY(`doctor_id`) REFERENCES `doctors`(`id`);
ALTER TABLE
    `documents` ADD CONSTRAINT FOREIGN KEY(`type_name`) REFERENCES `document_types`(`type`);
ALTER TABLE
    `clinic_records` ADD CONSTRAINT FOREIGN KEY(`clinic_id`) REFERENCES `clinics`(`id`);
ALTER TABLE
    `doctors_records` ADD CONSTRAINT FOREIGN KEY(`record_id`) REFERENCES `doctor_records`(`id`);
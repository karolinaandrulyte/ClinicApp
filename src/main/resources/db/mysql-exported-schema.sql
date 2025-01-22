CREATE TABLE `doctors`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `date_of_birth` DATE NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `specialty_name` VARCHAR(255) NOT NULL,
    `clinic_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `specialties`(
    `name` VARCHAR(255) NOT NULL PRIMARY KEY
);
CREATE TABLE `doctors_specialties`(
    `doctor_id` BIGINT UNSIGNED NOT NULL,
    `specialty_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`doctor_id`, `specialty_name`)
);
CREATE TABLE `doctor_records`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `status_name` VARCHAR(255) NOT NULL,
    `updated` DATETIME NOT NULL,
    `doctor_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `record_status`(
    `status` VARCHAR(255) NOT NULL PRIMARY KEY
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
    `city_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `cities`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `country_iso_code` VARCHAR(255) NOT NULL
);
CREATE TABLE `countries`(
    `iso_code` VARCHAR(255) NOT NULL PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);
CREATE TABLE `documents`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type` ENUM('CONSENT_FORM', 'PRESCRIPTION', 'LAB_REPORT', 'PAYMENT_RECEIPT', 'EMPLOYEE_RECORD') NOT NULL,
    `content` BLOB NOT NULL,
    `clinic_id` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `clinic_records`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `clinic_id` BIGINT UNSIGNED NOT NULL,
    `updated` DATETIME NOT NULL,
    `status_name` VARCHAR(255) NOT NULL
);
CREATE TABLE `clinic_status`(
    `status` VARCHAR(255) NOT NULL PRIMARY KEY
);

ALTER TABLE
    `doctors_specialties` ADD CONSTRAINT FOREIGN KEY(`specialty_name`) REFERENCES `specialties`(`name`);
ALTER TABLE
    `doctors_specialties` ADD CONSTRAINT FOREIGN KEY(`doctor_id`) REFERENCES `doctors`(`id`);
ALTER TABLE
    `doctors` ADD CONSTRAINT FOREIGN KEY(`id`) REFERENCES `doctors_clinics`(`doctor_id`);
ALTER TABLE
    `doctor_records` ADD CONSTRAINT FOREIGN KEY (`status_name`) REFERENCES `record_status`(`status`);
ALTER TABLE
    `doctor_records` ADD CONSTRAINT FOREIGN KEY (`doctor_id`) REFERENCES `doctors`(`id`);
ALTER TABLE
    `clinics` ADD CONSTRAINT FOREIGN KEY(`city_id`) REFERENCES `cities`(`id`);
ALTER TABLE
    `record_status` ADD CONSTRAINT FOREIGN KEY(`status`) REFERENCES `doctor_records`(`status_name`);
ALTER TABLE
    `cities` ADD CONSTRAINT FOREIGN KEY(`country_iso_code`) REFERENCES `countries`(`iso_code`);
ALTER TABLE
    `doctors_clinics` ADD CONSTRAINT FOREIGN KEY(`clinic_id`) REFERENCES `clinics`(`id`);
ALTER TABLE
    `doctors_clinics` ADD CONSTRAINT FOREIGN KEY (`doctor_id`) REFERENCES `doctors`(`id`);
ALTER TABLE
    `documents` MODIFY COLUMN `type` VARCHAR(255) NOT NULL;
ALTER TABLE
    `documents` ADD CONSTRAINT FOREIGN KEY (`clinic_id`) REFERENCES `clinics`(`id`);
ALTER TABLE
    `clinic_records` ADD CONSTRAINT FOREIGN KEY(`clinic_id`) REFERENCES `clinics`(`id`);
ALTER TABLE
    `clinic_records` ADD CONSTRAINT FOREIGN KEY(`status_name`) REFERENCES `clinic_status`(`status`);
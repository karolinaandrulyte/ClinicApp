CREATE TABLE IF NOT EXISTS `doctor_types` (
    `type` VARCHAR(255) NOT NULL PRIMARY KEY
);

ALTER TABLE `doctors` ADD COLUMN `doctor_type` VARCHAR(255);
ALTER TABLE `doctors` ADD CONSTRAINT FOREIGN KEY (`doctor_type`) REFERENCES `doctor_types`(`type`);

INSERT INTO `doctor_types` (`type`) VALUES
    ('INTERN'),
    ('RESIDENT'),
    ('CHIEF');

UPDATE `doctors` SET `doctor_type` = 'INTERN' WHERE `doctor_type` IS NULL;

ALTER TABLE `doctors` MODIFY COLUMN `doctor_type` VARCHAR(255) NOT NULL;
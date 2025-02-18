CREATE TABLE IF NOT EXISTS `document_types`(
    `type` VARCHAR(255) NOT NULL PRIMARY KEY,
    `description` VARCHAR(255) NOT NULL
);

ALTER TABLE
    `documents` ADD CONSTRAINT FOREIGN KEY (`type`) REFERENCES `document_types`(`type`);
ALTER TABLE
    `documents` MODIFY COLUMN `type` VARCHAR(255) NOT NULL;

INSERT INTO `document_types`(`type`, `description`) VALUES ('CONSENT_FORM', 'Consent Form'),
                                                           ('PRESCRIPTION', 'Prescription'),
                                                           ('LAB_REPORT', 'Lab Report'),
                                                           ('PAYMENT_RECEIPT', 'Payment Receipt'),
                                                           ('EMPLOYEE_RECORD', 'Employee Record'),
                                                           ('OTHER', 'Other');
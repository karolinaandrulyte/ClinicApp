ALTER TABLE doctor_types ADD COLUMN description VARCHAR(255);

UPDATE doctor_types SET description = 'Intern' WHERE type = 'INTERN';
UPDATE doctor_types SET description = 'Resident' WHERE type = 'RESIDENT';
UPDATE doctor_types SET description = 'Chief of department' WHERE type = 'CHIEF';

ALTER TABLE doctor_types MODIFY COLUMN description VARCHAR(255) NOT NULL;

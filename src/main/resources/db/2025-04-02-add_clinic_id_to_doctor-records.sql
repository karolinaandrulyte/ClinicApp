ALTER TABLE record_status ADD COLUMN description VARCHAR(255);

INSERT INTO record_status (status, description) VALUES
    ('EMPLOYED', 'Employed'),
    ('UNEMPLOYED', 'Unemployed');

ALTER TABLE record_status MODIFY COLUMN description VARCHAR(255) NOT NULL;

ALTER TABLE doctor_records ADD COLUMN clinic_id BIGINT UNSIGNED NOT NULL;

UPDATE doctor_records SET clinic_id = 6 WHERE clinic_id IS NULL;

ALTER TABLE doctor_records
ADD CONSTRAINT FOREIGN KEY (clinic_id) REFERENCES clinics(id) ON DELETE CASCADE;
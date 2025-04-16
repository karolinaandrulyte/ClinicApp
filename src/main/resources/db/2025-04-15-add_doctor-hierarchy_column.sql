ALTER TABLE doctors DROP COLUMN doctor_type_old;

ALTER TABLE doctors ADD CONSTRAINT FOREIGN KEY (doctor_type) REFERENCES doctor_types(type);

ALTER TABLE doctors ADD COLUMN doctor_hierarchy VARCHAR(255);
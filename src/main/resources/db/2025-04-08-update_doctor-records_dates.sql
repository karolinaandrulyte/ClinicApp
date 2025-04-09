ALTER TABLE doctor_records
ADD COLUMN status_start_date DATE NOT NULL DEFAULT (CURRENT_DATE),
ADD COLUMN status_end_date DATE NULL;

ALTER TABLE doctor_records
MODIFY COLUMN updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE doctor_records
  MODIFY COLUMN doctor_id BIGINT UNSIGNED AFTER id,
  MODIFY COLUMN clinic_id BIGINT UNSIGNED AFTER doctor_id,
  MODIFY COLUMN status_name VARCHAR(255) AFTER clinic_id,
  MODIFY COLUMN status_start_date DATE AFTER status_name,
  MODIFY COLUMN status_end_date DATE AFTER status_start_date,
  MODIFY COLUMN updated DATETIME AFTER status_end_date;
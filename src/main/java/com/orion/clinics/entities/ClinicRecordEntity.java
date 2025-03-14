package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clinic_records")
public class ClinicRecordEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "status_name")
        private ClinicStatusEntity status;

        @Column(name = "updated", nullable = false)
        private LocalDateTime updated;

        @ManyToOne
        @JoinColumn(name = "clinic_id")
        private ClinicEntity clinic;

        public static Builder builder() {
                return new Builder();
        }

        public static class Builder {
                private Long id;
                private ClinicStatusEntity status;
                private LocalDateTime updated;
                private ClinicEntity clinic;

                public ClinicRecordEntity build() {
                        return new ClinicRecordEntity(id, status, updated, clinic);
                }

                public Builder setId(Long id) {
                        this.id = id;
                        return this;
                }

                public Builder setStatus(ClinicStatusEntity status) {
                        this.status = status;
                        return this;
                }

                public Builder setUpdated(LocalDateTime updated) {
                        this.updated = updated;
                        return this;
                }

                public Builder setClinic(ClinicEntity clinic) {
                        this.clinic = clinic;
                        return this;
                }

        }
}
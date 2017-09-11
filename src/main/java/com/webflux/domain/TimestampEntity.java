package com.webflux.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class TimestampEntity {

    @Column(name = "created_datetime")
    private Timestamp createdDateTime;

    @Column(name = "updated_datetime", updatable = true)
    private Timestamp updatedDateTime;

    @PrePersist
    public void prePersist() {
        Timestamp ts = new Timestamp((new Date()).getTime());
        this.createdDateTime = ts;
        this.updatedDateTime = ts;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDateTime = new Timestamp((new Date()).getTime());
    }

}

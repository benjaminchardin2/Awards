package com.bencha.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.sql.Column;

import javax.annotation.processing.Generated;

/**
 * Pronostic is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class Pronostic extends com.coreoz.plume.db.querydsl.crud.CrudEntityQuerydsl {

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("award_id")
    private Long awardId;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id")
    private Long id;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("tdmb_movie_id")
    private Long tdmbMovieId;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("tdmb_person_id")
    private Long tdmbPersonId;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("user_participation_id")
    private Long userParticipationId;

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTdmbMovieId() {
        return tdmbMovieId;
    }

    public void setTdmbMovieId(Long tdmbMovieId) {
        this.tdmbMovieId = tdmbMovieId;
    }

    public Long getTdmbPersonId() {
        return tdmbPersonId;
    }

    public void setTdmbPersonId(Long tdmbPersonId) {
        this.tdmbPersonId = tdmbPersonId;
    }

    public Long getUserParticipationId() {
        return userParticipationId;
    }

    public void setUserParticipationId(Long userParticipationId) {
        this.userParticipationId = userParticipationId;
    }

    @Override
    public String toString() {
        return "Pronostic#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) {
            return super.equals(o);
        }
        if (!(o instanceof Pronostic)) {
            return false;
        }
        Pronostic obj = (Pronostic) o;
        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        return result;
    }

}


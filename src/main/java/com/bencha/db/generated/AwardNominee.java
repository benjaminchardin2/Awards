package com.bencha.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;

/**
 * AwardNominee is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class AwardNominee extends com.coreoz.plume.db.querydsl.crud.CrudEntityQuerydsl {

    @Column("award_id")
    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long awardId;

    @Column("id")
    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long id;

    @Column("tdmb_movie_id")
    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long tdmbMovieId;

    @Column("tdmb_person_id")
    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long tdmbPersonId;

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

    @Override
    public String toString() {
        return "AwardNominee#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) {
            return super.equals(o);
        }
        if (!(o instanceof AwardNominee)) {
            return false;
        }
        AwardNominee obj = (AwardNominee) o;
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


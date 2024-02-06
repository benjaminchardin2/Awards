package com.bencha.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;

/**
 * User is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class User extends com.coreoz.plume.db.querydsl.crud.CrudEntityQuerydsl {

    @Column("creation_date")
    private java.time.Instant creationDate;

    @Column("email")
    private String email;

    @Column("first_name")
    private String firstName;

    @Column("google_sub")
    private String googleSub;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id")
    private Long id;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id_role")
    private Long idRole;

    @Column("last_name")
    private String lastName;

    @Column("password")
    private String password;

    @Column("rgpd_ok")
    private Boolean rgpdOk;

    @Column("user_hashtag")
    private String userHashtag;

    @Column("user_name")
    private String userName;

    @Column("validated")
    private Boolean validated;

    public java.time.Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.time.Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGoogleSub() {
        return googleSub;
    }

    public void setGoogleSub(String googleSub) {
        this.googleSub = googleSub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRgpdOk() {
        return rgpdOk;
    }

    public void setRgpdOk(Boolean rgpdOk) {
        this.rgpdOk = rgpdOk;
    }

    public String getUserHashtag() {
        return userHashtag;
    }

    public void setUserHashtag(String userHashtag) {
        this.userHashtag = userHashtag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    @Override
    public String toString() {
        return "User#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) {
            return super.equals(o);
        }
        if (!(o instanceof User)) {
            return false;
        }
        User obj = (User) o;
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


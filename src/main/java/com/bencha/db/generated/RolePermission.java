package com.bencha.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;

/**
 * RolePermission is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class RolePermission {

    @Column("id_role")
    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long idRole;

    @Column("permission")
    private String permission;

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "RolePermission#" + idRole+ ";" + permission;
    }

    @Override
    public boolean equals(Object o) {
        if (idRole == null || permission == null) {
            return super.equals(o);
        }
        if (!(o instanceof RolePermission)) {
            return false;
        }
        RolePermission obj = (RolePermission) o;
        return idRole.equals(obj.idRole) && permission.equals(obj.permission);
    }

    @Override
    public int hashCode() {
        if (idRole == null || permission == null) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + idRole.hashCode();
        result = prime * result + permission.hashCode();
        return result;
    }

}


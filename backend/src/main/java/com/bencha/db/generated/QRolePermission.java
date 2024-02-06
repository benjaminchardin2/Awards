package com.bencha.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QRolePermission is a Querydsl query type for RolePermission
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QRolePermission extends com.querydsl.sql.RelationalPathBase<RolePermission> {

    private static final long serialVersionUID = -1706601468;

    public static final QRolePermission rolePermission = new QRolePermission("PLM_ROLE_PERMISSION");

    public final NumberPath<Long> idRole = createNumber("idRole", Long.class);

    public final StringPath permission = createString("permission");

    public final com.querydsl.sql.PrimaryKey<RolePermission> primary = createPrimaryKey(idRole, permission);

    public final com.querydsl.sql.ForeignKey<Role> plmRolePermissionRole = createForeignKey(idRole, "id");

    public QRolePermission(String variable) {
        super(RolePermission.class, forVariable(variable), "null", "PLM_ROLE_PERMISSION");
        addMetadata();
    }

    public QRolePermission(String variable, String schema, String table) {
        super(RolePermission.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QRolePermission(String variable, String schema) {
        super(RolePermission.class, forVariable(variable), schema, "PLM_ROLE_PERMISSION");
        addMetadata();
    }

    public QRolePermission(Path<? extends RolePermission> path) {
        super(path.getType(), path.getMetadata(), "null", "PLM_ROLE_PERMISSION");
        addMetadata();
    }

    public QRolePermission(PathMetadata metadata) {
        super(RolePermission.class, metadata, "null", "PLM_ROLE_PERMISSION");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(idRole, ColumnMetadata.named("id_role").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(permission, ColumnMetadata.named("permission").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


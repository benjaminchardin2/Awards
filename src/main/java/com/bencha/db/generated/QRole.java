package com.bencha.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import java.util.*;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QRole extends com.querydsl.sql.RelationalPathBase<Role> {

    private static final long serialVersionUID = -428976043;

    public static final QRole role = new QRole("PLM_ROLE");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final com.querydsl.sql.PrimaryKey<Role> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<RolePermission> _plmRolePermissionRole = createInvForeignKey(Arrays.asList(id, id), Arrays.asList("id_role", "id_role"));

    public final com.querydsl.sql.ForeignKey<User> _plmUserRole = createInvForeignKey(Arrays.asList(id, id), Arrays.asList("id_role", "id_role"));

    public QRole(String variable) {
        super(Role.class, forVariable(variable), "null", "PLM_ROLE");
        addMetadata();
    }

    public QRole(String variable, String schema, String table) {
        super(Role.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QRole(String variable, String schema) {
        super(Role.class, forVariable(variable), schema, "PLM_ROLE");
        addMetadata();
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata(), "null", "PLM_ROLE");
        addMetadata();
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata, "null", "PLM_ROLE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(label, ColumnMetadata.named("label").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


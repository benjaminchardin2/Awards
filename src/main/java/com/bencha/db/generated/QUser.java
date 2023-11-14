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
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUser extends com.querydsl.sql.RelationalPathBase<User> {

    private static final long serialVersionUID = -428883030;

    public static final QUser user = new QUser("PLM_USER");

    public final DateTimePath<java.time.Instant> creationDate = createDateTime("creationDate", java.time.Instant.class);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> idRole = createNumber("idRole", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath password = createString("password");

    public final StringPath userName = createString("userName");

    public final com.querydsl.sql.PrimaryKey<User> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<Role> plmUserRole = createForeignKey(idRole, "id");

    public final com.querydsl.sql.ForeignKey<UserParticipation> _awdUserParticipationIbfk2 = createInvForeignKey(Arrays.asList(id, id, id), Arrays.asList("user_id", "user_id", "user_id"));

    public QUser(String variable) {
        super(User.class, forVariable(variable), "null", "PLM_USER");
        addMetadata();
    }

    public QUser(String variable, String schema, String table) {
        super(User.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUser(String variable, String schema) {
        super(User.class, forVariable(variable), schema, "PLM_USER");
        addMetadata();
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata(), "null", "PLM_USER");
        addMetadata();
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata, "null", "PLM_USER");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(creationDate, ColumnMetadata.named("creation_date").withIndex(3).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(email, ColumnMetadata.named("email").withIndex(6).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(firstName, ColumnMetadata.named("first_name").withIndex(4).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(idRole, ColumnMetadata.named("id_role").withIndex(2).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(lastName, ColumnMetadata.named("last_name").withIndex(5).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(password, ColumnMetadata.named("password").withIndex(8).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(userName, ColumnMetadata.named("user_name").withIndex(7).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


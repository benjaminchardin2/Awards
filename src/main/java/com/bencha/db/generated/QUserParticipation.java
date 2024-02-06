package com.bencha.db.generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QUserParticipation is a Querydsl query type for UserParticipation
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserParticipation extends com.querydsl.sql.RelationalPathBase<UserParticipation> {

    private static final long serialVersionUID = 426622391;

    public static final QUserParticipation userParticipation = new QUserParticipation("awd_user_participation");

    public final NumberPath<Long> ceremonyId = createNumber("ceremonyId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath shareCode = createString("shareCode");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.querydsl.sql.PrimaryKey<UserParticipation> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<Ceremony> awdUserParticipationIbfk1 = createForeignKey(ceremonyId, "id");

    public final com.querydsl.sql.ForeignKey<User> awdUserParticipationIbfk2 = createForeignKey(userId, "id");

    public final com.querydsl.sql.ForeignKey<Pronostic> _awdPronosticIbfk1 = createInvForeignKey(id, "user_participation_id");

    public QUserParticipation(String variable) {
        super(UserParticipation.class, forVariable(variable), "null", "awd_user_participation");
        addMetadata();
    }

    public QUserParticipation(String variable, String schema, String table) {
        super(UserParticipation.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserParticipation(String variable, String schema) {
        super(UserParticipation.class, forVariable(variable), schema, "awd_user_participation");
        addMetadata();
    }

    public QUserParticipation(Path<? extends UserParticipation> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_user_participation");
        addMetadata();
    }

    public QUserParticipation(PathMetadata metadata) {
        super(UserParticipation.class, metadata, "null", "awd_user_participation");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(ceremonyId, ColumnMetadata.named("ceremony_id").withIndex(4).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(shareCode, ColumnMetadata.named("share_code").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(userId, ColumnMetadata.named("user_id").withIndex(2).ofType(Types.BIGINT).withSize(19));
    }

}


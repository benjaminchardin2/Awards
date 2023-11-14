package com.bencha.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QCeremony is a Querydsl query type for Ceremony
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QCeremony extends com.querydsl.sql.RelationalPathBase<Ceremony> {

    private static final long serialVersionUID = -1083145503;

    public static final QCeremony ceremony = new QCeremony("awd_ceremony");

    public final StringPath avatarUrl = createString("avatarUrl");

    public final DateTimePath<java.time.Instant> ceremonyDate = createDateTime("ceremonyDate", java.time.Instant.class);

    public final StringPath description = createString("description");

    public final BooleanPath hasNominees = createBoolean("hasNominees");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isHighlighted = createBoolean("isHighlighted");

    public final StringPath name = createString("name");

    public final StringPath pictureUrl = createString("pictureUrl");

    public final StringPath shortName = createString("shortName");

    public final com.querydsl.sql.PrimaryKey<Ceremony> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<Award> _awdAwardIbfk1 = createInvForeignKey(id, "ceremony_id");

    public final com.querydsl.sql.ForeignKey<UserParticipation> _awdUserParticipationIbfk1 = createInvForeignKey(id, "ceremony_id");

    public QCeremony(String variable) {
        super(Ceremony.class, forVariable(variable), "null", "awd_ceremony");
        addMetadata();
    }

    public QCeremony(String variable, String schema, String table) {
        super(Ceremony.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCeremony(String variable, String schema) {
        super(Ceremony.class, forVariable(variable), schema, "awd_ceremony");
        addMetadata();
    }

    public QCeremony(Path<? extends Ceremony> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_ceremony");
        addMetadata();
    }

    public QCeremony(PathMetadata metadata) {
        super(Ceremony.class, metadata, "null", "awd_ceremony");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(avatarUrl, ColumnMetadata.named("avatar_url").withIndex(9).ofType(Types.VARCHAR).withSize(2048));
        addMetadata(ceremonyDate, ColumnMetadata.named("ceremony_date").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("description").withIndex(7).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(hasNominees, ColumnMetadata.named("has_nominees").withIndex(3).ofType(Types.BIT).withSize(1).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(isHighlighted, ColumnMetadata.named("is_highlighted").withIndex(5).ofType(Types.BIT).withSize(1).notNull());
        addMetadata(name, ColumnMetadata.named("name").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(pictureUrl, ColumnMetadata.named("picture_url").withIndex(4).ofType(Types.VARCHAR).withSize(2048));
        addMetadata(shortName, ColumnMetadata.named("short_name").withIndex(6).ofType(Types.VARCHAR).withSize(255));
    }

}


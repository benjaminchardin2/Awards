package com.bencha.db.generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QPronostic is a Querydsl query type for Pronostic
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPronostic extends com.querydsl.sql.RelationalPathBase<Pronostic> {

    private static final long serialVersionUID = 1833944298;

    public static final QPronostic pronostic = new QPronostic("awd_pronostic");

    public final NumberPath<Long> awardId = createNumber("awardId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> nomineeId = createNumber("nomineeId", Long.class);

    public final NumberPath<Long> tdmbMovieId = createNumber("tdmbMovieId", Long.class);

    public final NumberPath<Long> tdmbPersonId = createNumber("tdmbPersonId", Long.class);

    public final NumberPath<Long> userParticipationId = createNumber("userParticipationId", Long.class);

    public final com.querydsl.sql.PrimaryKey<Pronostic> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<AwardNominee> awdPronosticAwdAwardNomineeIdFk = createForeignKey(nomineeId, "id");

    public final com.querydsl.sql.ForeignKey<UserParticipation> awdPronosticIbfk1 = createForeignKey(userParticipationId, "id");

    public final com.querydsl.sql.ForeignKey<Award> awdPronosticIbfk2 = createForeignKey(awardId, "id");

    public QPronostic(String variable) {
        super(Pronostic.class, forVariable(variable), "null", "awd_pronostic");
        addMetadata();
    }

    public QPronostic(String variable, String schema, String table) {
        super(Pronostic.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPronostic(String variable, String schema) {
        super(Pronostic.class, forVariable(variable), schema, "awd_pronostic");
        addMetadata();
    }

    public QPronostic(Path<? extends Pronostic> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_pronostic");
        addMetadata();
    }

    public QPronostic(PathMetadata metadata) {
        super(Pronostic.class, metadata, "null", "awd_pronostic");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(awardId, ColumnMetadata.named("award_id").withIndex(5).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(nomineeId, ColumnMetadata.named("nominee_id").withIndex(6).ofType(Types.BIGINT).withSize(19));
        addMetadata(tdmbMovieId, ColumnMetadata.named("tdmb_movie_id").withIndex(3).ofType(Types.BIGINT).withSize(19));
        addMetadata(tdmbPersonId, ColumnMetadata.named("tdmb_person_id").withIndex(4).ofType(Types.BIGINT).withSize(19));
        addMetadata(userParticipationId, ColumnMetadata.named("user_participation_id").withIndex(2).ofType(Types.BIGINT).withSize(19).notNull());
    }

}


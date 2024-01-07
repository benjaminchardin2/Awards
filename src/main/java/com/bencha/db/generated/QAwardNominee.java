package com.bencha.db.generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QAwardNominee is a Querydsl query type for AwardNominee
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAwardNominee extends com.querydsl.sql.RelationalPathBase<AwardNominee> {

    private static final long serialVersionUID = -72254189;

    public static final QAwardNominee awardNominee = new QAwardNominee("awd_award_nominee");

    public final NumberPath<Long> awardId = createNumber("awardId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> tdmbMovieId = createNumber("tdmbMovieId", Long.class);

    public final NumberPath<Long> tdmbPersonId = createNumber("tdmbPersonId", Long.class);

    public final com.querydsl.sql.PrimaryKey<AwardNominee> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<Award> awdAwardNomineeIbfk1 = createForeignKey(awardId, "id");

    public QAwardNominee(String variable) {
        super(AwardNominee.class, forVariable(variable), "null", "awd_award_nominee");
        addMetadata();
    }

    public QAwardNominee(String variable, String schema, String table) {
        super(AwardNominee.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAwardNominee(String variable, String schema) {
        super(AwardNominee.class, forVariable(variable), schema, "awd_award_nominee");
        addMetadata();
    }

    public QAwardNominee(Path<? extends AwardNominee> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_award_nominee");
        addMetadata();
    }

    public QAwardNominee(PathMetadata metadata) {
        super(AwardNominee.class, metadata, "null", "awd_award_nominee");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(awardId, ColumnMetadata.named("award_id").withIndex(4).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(tdmbMovieId, ColumnMetadata.named("tdmb_movie_id").withIndex(2).ofType(Types.BIGINT).withSize(19));
        addMetadata(tdmbPersonId, ColumnMetadata.named("tdmb_person_id").withIndex(3).ofType(Types.BIGINT).withSize(19));
    }

}


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
 * QAward is a Querydsl query type for Award
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAward extends com.querydsl.sql.RelationalPathBase<Award> {

    private static final long serialVersionUID = -428827042;

    public static final QAward award = new QAward("awd_award");

    public final NumberPath<Long> ceremonyId = createNumber("ceremonyId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath type = createString("type");

    public final com.querydsl.sql.PrimaryKey<Award> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<Ceremony> awdAwardIbfk1 = createForeignKey(ceremonyId, "id");

    public final com.querydsl.sql.ForeignKey<AwardCondition> _awdAwardConditionIbfk1 = createInvForeignKey(id, "award_id");

    public final com.querydsl.sql.ForeignKey<AwardNominee> _awdAwardNomineeIbfk1 = createInvForeignKey(id, "award_id");

    public final com.querydsl.sql.ForeignKey<Pronostic> _awdPronosticIbfk2 = createInvForeignKey(id, "award_id");

    public QAward(String variable) {
        super(Award.class, forVariable(variable), "null", "awd_award");
        addMetadata();
    }

    public QAward(String variable, String schema, String table) {
        super(Award.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAward(String variable, String schema) {
        super(Award.class, forVariable(variable), schema, "awd_award");
        addMetadata();
    }

    public QAward(Path<? extends Award> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_award");
        addMetadata();
    }

    public QAward(PathMetadata metadata) {
        super(Award.class, metadata, "null", "awd_award");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(ceremonyId, ColumnMetadata.named("ceremony_id").withIndex(3).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(name, ColumnMetadata.named("name").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(type, ColumnMetadata.named("type").withIndex(4).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


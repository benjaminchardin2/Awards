package com.bencha.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAwardCondition is a Querydsl query type for AwardCondition
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAwardCondition extends com.querydsl.sql.RelationalPathBase<AwardCondition> {

    private static final long serialVersionUID = -1569441699;

    public static final QAwardCondition awardCondition = new QAwardCondition("awd_award_condition");

    public final NumberPath<Long> awardId = createNumber("awardId", Long.class);

    public final StringPath conditionName = createString("conditionName");

    public final StringPath conditionType = createString("conditionType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath value = createString("value");

    public final com.querydsl.sql.PrimaryKey<AwardCondition> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<Award> awdAwardConditionIbfk1 = createForeignKey(awardId, "id");

    public QAwardCondition(String variable) {
        super(AwardCondition.class, forVariable(variable), "null", "awd_award_condition");
        addMetadata();
    }

    public QAwardCondition(String variable, String schema, String table) {
        super(AwardCondition.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAwardCondition(String variable, String schema) {
        super(AwardCondition.class, forVariable(variable), schema, "awd_award_condition");
        addMetadata();
    }

    public QAwardCondition(Path<? extends AwardCondition> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_award_condition");
        addMetadata();
    }

    public QAwardCondition(PathMetadata metadata) {
        super(AwardCondition.class, metadata, "null", "awd_award_condition");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(awardId, ColumnMetadata.named("award_id").withIndex(4).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(conditionName, ColumnMetadata.named("condition_name").withIndex(3).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(conditionType, ColumnMetadata.named("condition_type").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(value, ColumnMetadata.named("value").withIndex(5).ofType(Types.VARCHAR).withSize(255));
    }

}


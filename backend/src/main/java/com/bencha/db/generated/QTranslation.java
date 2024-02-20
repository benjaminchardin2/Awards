package com.bencha.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QTranslation is a Querydsl query type for Translation
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QTranslation extends com.querydsl.sql.RelationalPathBase<Translation> {

    private static final long serialVersionUID = 1458000370;

    public static final QTranslation translation = new QTranslation("awd_translation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath source = createString("source");

    public final StringPath translationValue = createString("translationValue");

    public final com.querydsl.sql.PrimaryKey<Translation> primary = createPrimaryKey(id);

    public QTranslation(String variable) {
        super(Translation.class, forVariable(variable), "null", "awd_translation");
        addMetadata();
    }

    public QTranslation(String variable, String schema, String table) {
        super(Translation.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTranslation(String variable, String schema) {
        super(Translation.class, forVariable(variable), schema, "awd_translation");
        addMetadata();
    }

    public QTranslation(Path<? extends Translation> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_translation");
        addMetadata();
    }

    public QTranslation(PathMetadata metadata) {
        super(Translation.class, metadata, "null", "awd_translation");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(source, ColumnMetadata.named("source").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(translationValue, ColumnMetadata.named("translation_value").withIndex(3).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


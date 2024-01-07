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
 * QLegal is a Querydsl query type for Legal
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QLegal extends com.querydsl.sql.RelationalPathBase<Legal> {

    private static final long serialVersionUID = -419199302;

    public static final QLegal legal = new QLegal("awd_legal");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath page = createString("page");

    public final com.querydsl.sql.PrimaryKey<Legal> primary = createPrimaryKey(id);

    public QLegal(String variable) {
        super(Legal.class, forVariable(variable), "null", "awd_legal");
        addMetadata();
    }

    public QLegal(String variable, String schema, String table) {
        super(Legal.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QLegal(String variable, String schema) {
        super(Legal.class, forVariable(variable), schema, "awd_legal");
        addMetadata();
    }

    public QLegal(Path<? extends Legal> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_legal");
        addMetadata();
    }

    public QLegal(PathMetadata metadata) {
        super(Legal.class, metadata, "null", "awd_legal");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(content, ColumnMetadata.named("content").withIndex(3).ofType(Types.LONGVARCHAR).withSize(65535).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(page, ColumnMetadata.named("page").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


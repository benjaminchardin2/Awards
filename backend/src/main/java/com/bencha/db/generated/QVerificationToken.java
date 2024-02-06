package com.bencha.db.generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QVerificationToken is a Querydsl query type for VerificationToken
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QVerificationToken extends com.querydsl.sql.RelationalPathBase<VerificationToken> {

    private static final long serialVersionUID = -1662666977;

    public static final QVerificationToken verificationToken = new QVerificationToken("awd_verification_token");

    public final DateTimePath<java.time.Instant> expirationDate = createDateTime("expirationDate", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath type = createString("type");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath verificationString = createString("verificationString");

    public final com.querydsl.sql.PrimaryKey<VerificationToken> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<User> awdVerificationTokenIbfk1 = createForeignKey(userId, "id");

    public QVerificationToken(String variable) {
        super(VerificationToken.class, forVariable(variable), "null", "awd_verification_token");
        addMetadata();
    }

    public QVerificationToken(String variable, String schema, String table) {
        super(VerificationToken.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QVerificationToken(String variable, String schema) {
        super(VerificationToken.class, forVariable(variable), schema, "awd_verification_token");
        addMetadata();
    }

    public QVerificationToken(Path<? extends VerificationToken> path) {
        super(path.getType(), path.getMetadata(), "null", "awd_verification_token");
        addMetadata();
    }

    public QVerificationToken(PathMetadata metadata) {
        super(VerificationToken.class, metadata, "null", "awd_verification_token");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(expirationDate, ColumnMetadata.named("expiration_date").withIndex(4).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(type, ColumnMetadata.named("type").withIndex(5).ofType(Types.VARCHAR).withSize(50).notNull());
        addMetadata(userId, ColumnMetadata.named("user_id").withIndex(2).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(verificationString, ColumnMetadata.named("verification_string").withIndex(3).ofType(Types.VARCHAR).withSize(255).notNull());
    }

}


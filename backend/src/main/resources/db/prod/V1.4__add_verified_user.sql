CREATE TABLE awd_verification_token
(
    id           bigint not null,
    user_id      bigint not null,
    verification_string         varchar(255) not null,
    expiration_date      timestamp not null,
    type          varchar(50) not null ,
    PRIMARY KEY (id),
    UNIQUE(verification_string),
    FOREIGN KEY (user_id) REFERENCES plm_user(id)
);

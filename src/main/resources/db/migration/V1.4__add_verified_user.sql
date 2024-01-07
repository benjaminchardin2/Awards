CREATE TABLE awd_verification_token
(
    id           bigint(20) not null,
    user_id      bigint(20) not null,
    verification_string         varchar(255) not null,
    expiration_date      datetime not null,
    type          varchar(50) not null ,
    PRIMARY KEY (id),
    UNIQUE KEY `uniq_verification_token` (`verification_string`),
    FOREIGN KEY (user_id) REFERENCES plm_user(id)
);

INSERT INTO PLM_ROLE VALUES(2, 'verified_user');
INSERT INTO PLM_ROLE_PERMISSION VALUES(2, 'VIEW_WEB');
INSERT INTO PLM_ROLE_PERMISSION VALUES(2, 'HEAVY_LOAD_ACTIONS');

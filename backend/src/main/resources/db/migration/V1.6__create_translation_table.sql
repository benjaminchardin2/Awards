CREATE TABLE awd_translation
(
    id           bigint(20) not null,
    source         varchar(255) not null,
    translation_value      varchar(255) not null,
    PRIMARY KEY (id)
);

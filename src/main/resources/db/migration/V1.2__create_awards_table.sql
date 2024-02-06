CREATE TABLE awd_ceremony
(
    id           bigint(20) not null,
    name         varchar(255) not null,
    picture_url  varchar(2048) null,
    avatar_url varchar(2048) null,
    has_nominees tinyint(1) not null default 0,
    is_highlighted tinyint(1) default false not null,
    short_name   varchar(255) null,
    description  text null,
    ceremony_date datetime null,
    PRIMARY KEY (id)
);

CREATE TABLE awd_award
(
    id           bigint(20) not null,
    name         varchar(255) not null,
    ceremony_id  bigint(20) not null,
    type         varchar(255) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (ceremony_id) REFERENCES awd_ceremony(id)
);

CREATE TABLE awd_award_nominee
(
    id           bigint(20) not null,
    tdmb_movie_id  bigint(20) null,
    tdmb_person_id  bigint(20) null,
    award_id bigint(20) not null,
    name_override text null,
    PRIMARY KEY (id),
    FOREIGN KEY (award_id) REFERENCES awd_award(id)
);

CREATE TABLE awd_award_condition
(
    id           bigint(20) not null,
    condition_type varchar(255) not null,
    condition_name varchar(255) not null,
    award_id bigint(20) not null,
    `value` varchar(255) null,
    PRIMARY KEY (id),
    FOREIGN KEY (award_id) REFERENCES awd_award(id)
);

CREATE TABLE awd_user_participation
(
    id           bigint(20) not null,
    user_id bigint(20) null,
    ceremony_id bigint(20) not null,
    share_code varchar(255) null,
    PRIMARY KEY (id),
    FOREIGN KEY (ceremony_id) REFERENCES awd_ceremony(id),
    FOREIGN KEY (user_id) REFERENCES plm_user(id)
);

CREATE TABLE awd_pronostic
(
    id           bigint(20) not null,
    user_participation_id bigint(20) not null,
    tdmb_movie_id  bigint(20) null,
    tdmb_person_id  bigint(20) null,
    award_id bigint(20) not null,
    nominee_id bigint(20) null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_participation_id) REFERENCES awd_user_participation(id),
    FOREIGN KEY (award_id) REFERENCES awd_award(id),
    FOREIGN KEY (nominee_id) REFERENCES awd_award_nominee(id)
);

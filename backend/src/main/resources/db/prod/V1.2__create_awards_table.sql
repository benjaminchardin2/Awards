CREATE TABLE awd_ceremony
(
    id           bigint not null,
    name         varchar(255) not null,
    picture_url  varchar(2048) null,
    avatar_url varchar(2048) null,
    has_nominees boolean not null default false,
    is_highlighted boolean default false not null,
    short_name   varchar(255) null,
    description  text null,
    ceremony_date timestamp null,
    PRIMARY KEY (id)
);

CREATE TABLE awd_award
(
    id           bigint not null,
    name         varchar(255) not null,
    ceremony_id  bigint not null,
    type         varchar(255) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (ceremony_id) REFERENCES awd_ceremony(id)
);

CREATE TABLE awd_award_nominee
(
    id           bigint not null,
    tdmb_movie_id  bigint null,
    tdmb_person_id  bigint null,
    award_id bigint not null,
    name_override text null,
    PRIMARY KEY (id),
    FOREIGN KEY (award_id) REFERENCES awd_award(id)
);

CREATE TABLE awd_award_condition
(
    id           bigint not null,
    condition_type varchar(255) not null,
    condition_name varchar(255) not null,
    award_id bigint not null,
    "value" varchar(255) null,
    PRIMARY KEY (id),
    FOREIGN KEY (award_id) REFERENCES awd_award(id)
);

CREATE TABLE awd_user_participation
(
    id           bigint not null,
    user_id bigint null,
    ceremony_id bigint not null,
    share_code varchar(255) null,
    PRIMARY KEY (id),
    FOREIGN KEY (ceremony_id) REFERENCES awd_ceremony(id),
    FOREIGN KEY (user_id) REFERENCES plm_user(id)
);

CREATE TABLE awd_pronostic
(
    id           bigint not null,
    user_participation_id bigint not null,
    tdmb_movie_id  bigint null,
    tdmb_person_id  bigint null,
    award_id bigint not null,
    nominee_id bigint null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_participation_id) REFERENCES awd_user_participation(id),
    FOREIGN KEY (award_id) REFERENCES awd_award(id),
    FOREIGN KEY (nominee_id) REFERENCES awd_award_nominee(id)
);

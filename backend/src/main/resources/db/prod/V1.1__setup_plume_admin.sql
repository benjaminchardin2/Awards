DROP TABLE IF EXISTS PLM_ROLE;
CREATE TABLE  PLM_ROLE (
                             id bigint NOT NULL,
                             label varchar(255) NOT NULL,
                             PRIMARY KEY  (id),
                             UNIQUE(label)
);

DROP TABLE IF EXISTS PLM_USER;
CREATE TABLE  PLM_USER (
                             id bigint NOT NULL,
                             id_role bigint NOT NULL,
                             creation_date timestamp NOT NULL,
                             first_name varchar(255) NULL,
                             last_name varchar(255) NULL,
                             email varchar(255) NOT NULL,
                             user_name varchar(255) NOT NULL,
                             password varchar(255) NULL,
                             validated boolean NOT NULL DEFAULT FALSE,
                             rgpd_ok boolean NOT NULL DEFAULT FALSE,
                             google_sub varchar(255) NULL,
                             user_hashtag varchar(255) NULL,
                             PRIMARY KEY  (id),
                             UNIQUE(email),
                             CONSTRAINT plm_user_role FOREIGN KEY (id_role) REFERENCES PLM_ROLE (id)
);

DROP TABLE IF EXISTS PLM_ROLE_PERMISSION;
CREATE TABLE  PLM_ROLE_PERMISSION (
                                        id_role bigint NOT NULL,
                                        permission varchar(255) NOT NULL,
                                        PRIMARY KEY (id_role, permission),
                                        CONSTRAINT plm_role_permission_role FOREIGN KEY (id_role) REFERENCES PLM_ROLE (id)
);


INSERT INTO PLM_ROLE (id, label) VALUES (1, 'user');
INSERT INTO PLM_ROLE (id, label) VALUES (2, 'verified_user');

INSERT INTO PLM_ROLE_PERMISSION (id_role, permission) VALUES (1, 'VIEW_WEB');
INSERT INTO PLM_ROLE_PERMISSION (id_role, permission) VALUES (2, 'HEAVY_LOAD_ACTIONS');
INSERT INTO PLM_ROLE_PERMISSION (id_role, permission) VALUES (2, 'VIEW_WEB');


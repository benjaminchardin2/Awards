DROP TABLE IF EXISTS PLM_ROLE;
CREATE TABLE  PLM_ROLE (
                             id bigint(20) NOT NULL,
                             label varchar(255) NOT NULL,
                             PRIMARY KEY  (id),
                             UNIQUE KEY uniq_plm_role_label (label)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PLM_USER;
CREATE TABLE  PLM_USER (
                             id bigint(20) NOT NULL,
                             id_role bigint(20) NOT NULL,
                             creation_date datetime NOT NULL,
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
                             UNIQUE KEY uniq_plm_user_email (email),
                             CONSTRAINT plm_user_role FOREIGN KEY (id_role) REFERENCES PLM_ROLE (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PLM_ROLE_PERMISSION;
CREATE TABLE  PLM_ROLE_PERMISSION (
                                        id_role bigint(20) NOT NULL,
                                        permission varchar(255) NOT NULL,
                                        PRIMARY KEY (id_role, permission),
                                        CONSTRAINT plm_role_permission_role FOREIGN KEY (id_role) REFERENCES PLM_ROLE (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO PLM_ROLE (id, label) VALUES (1, 'user');
INSERT INTO PLM_ROLE (id, label) VALUES (2, 'verified_user');

INSERT INTO PLM_ROLE_PERMISSION (id_role, permission) VALUES (1, 'VIEW_WEB');
INSERT INTO PLM_ROLE_PERMISSION (id_role, permission) VALUES (2, 'HEAVY_LOAD_ACTIONS');
INSERT INTO PLM_ROLE_PERMISSION (id_role, permission) VALUES (2, 'VIEW_WEB');


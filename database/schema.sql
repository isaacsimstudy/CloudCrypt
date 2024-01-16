DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;
-- Import extension for uuid_generate_v4()
-- Use gen_random_uuid() if not importing uuid-ossp
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE user_profile
(
    PRIMARY KEY (uuid),
    uuid           uuid         DEFAULT uuid_generate_v4(),
    is_active      boolean      NOT NULL DEFAULT true,
    privilege      varchar(255) NOT NULL CHECK ( privilege IN ('customer', 'admin', 'owner')),
    title          varchar(255) NOT NULL UNIQUE,
    CHECK (title != 'customer' OR privilege = 'customer')
);

CREATE INDEX ON user_profile (privilege);
CREATE INDEX ON user_profile (title);

CREATE TABLE user_account
(
    PRIMARY KEY (uuid),
    uuid            uuid        NOT NULL DEFAULT uuid_generate_v4(),
    is_active       boolean     NOT NULL DEFAULT true,
    user_profile    uuid        NOT NULL REFERENCES user_profile(uuid) ON UPDATE CASCADE,
    password_hash   VARCHAR(72) NOT NULL CHECK (length(password_hash) <= 72),

    -- Viewable to user
    username        Citext       NOT NULL UNIQUE,
    email           Citext       NOT NULL UNIQUE,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    address         VARCHAR(255) NOT NULL,               -- Users can enter their address in any format.
    date_of_birth   DATE         NOT NULL,               -- SELECT EXTRACT(YEAR FROM AGE(NOW(), date_of_birth)) FROM user_account;
    time_created    Timestamptz  NOT NULL DEFAULT NOW(), -- Long-term user benefits.
    time_last_login Timestamptz  NOT NULL DEFAULT NOW()  -- User inactivity.
);

CREATE TABLE customer_details
(
    uuid             uuid        NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (uuid) REFERENCES user_account(uuid) ON UPDATE CASCADE,
    sub_tier         varchar(75) NOT NULL CHECK ( sub_tier IN ('free', 'premium'))
);

CREATE TABLE key
(
    uuid            uuid        NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    FOREIGN KEY (uuid) REFERENCES user_account(uuid) ON UPDATE CASCADE,
    name         varchar(255) NOT NULL UNIQUE,
    password_hash   VARCHAR(72) NOT NULL CHECK (length(password_hash) <= 72)
);

CREATE TABLE file_info
(
    log_id         uuid       NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    file_id        uuid       NOT NULL REFERENCES Cloud(file_id) ON UPDATE CASCADE,
    user_account   uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    original_size  bigint     NOT NULL,
    file_type      varchar(255) NOT NULL,
    original_hash  varchar(255) NOT NULL,
    time_uploaded  Timestamptz NOT NULL,
    last_modified  Timestamptz NOT NULL DEFAULT NOW(),
    file_owner     uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    encryption_type varchar(255) NOT NULL CHECK ( encryption_type IN ('GCM', 'CCM')),
    tags            varchar(255) NOT NULL
);

CREATE TABLE Cloud
(
    file_id        uuid       NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    user_account   uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    file_name      varchar(255) NOT NULL UNIQUE,
    encrypted_file bytea      NOT NULL,
    key_id         uuid       NOT NULL REFERENCES key(uuid) ON UPDATE CASCADE,
    status         varchar(255) NOT NULL CHECK ( status IN ('active', 'deleted')),
    checksum       varchar(255) NOT NULL UNIQUE
);




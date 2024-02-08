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
    -- Viewable to admin
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
    time_last_login Timestamptz  NOT NULL DEFAULT NOW(),  -- User inactivity.
    phone_number    VARCHAR(255) NOT NULL
);

CREATE TABLE login_settings
(
    user_account    uuid        NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    login_id        uuid        NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    login_attempts  int         NOT NULL DEFAULT 0,
    login_status    varchar(255) NOT NULL CHECK ( login_status IN ('active', 'inactive')),
    login_time      Timestamptz NOT NULL DEFAULT NOW(),
    two_factor_auth varchar(255) NOT NULL CHECK ( two_factor_auth IN ('active', 'inactive'))
);

CREATE TABLE customer_details
(
    user_account     uuid       NOT NULL UNIQUE REFERENCES user_account(uuid) ON UPDATE CASCADE,
    customer_details_id           uuid        NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    sub_tier         varchar(75) NOT NULL CHECK ( sub_tier IN ('free', 'premium'))
);

CREATE TABLE notification_settings
(
    user_account    uuid        NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    notification_id uuid        NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    notification_type varchar(255) NOT NULL CHECK ( notification_type IN ('all', 'login', 'logout', 'upload', 'download', 'delete', 'share', 'unshare')),
    notification_method varchar(255) NOT NULL CHECK ( notification_method IN ('email')),
    status         varchar(255) NOT NULL CHECK ( status IN ('active', 'inactive')),
    notification_frequency varchar(255) NOT NULL CHECK ( notification_frequency IN ('immediate', 'daily', 'weekly', 'monthly'))
);

CREATE TABLE key
(
    key_id        uuid        NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    uuid           uuid        NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    name         varchar(255) NOT NULL UNIQUE,
    password_hash   VARCHAR(72) NOT NULL CHECK (length(password_hash) <= 72)
);

CREATE TABLE Cloud
(
    file_id        uuid       NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    user_account   uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    file_name      varchar(255) NOT NULL UNIQUE,
    encrypted_file bytea      NOT NULL,
    key_id         uuid       NOT NULL REFERENCES key(key_id) ON UPDATE CASCADE,
    status         varchar(255) NOT NULL CHECK ( status IN ('active', 'deleted')),
    checksum       varchar(255) NOT NULL UNIQUE
);

CREATE TABLE activity_log
(
    activity_id     uuid        NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    user_account    uuid        NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    activity_type   varchar(255) NOT NULL CHECK ( activity_type IN ('login', 'logout', 'upload', 'download', 'delete', 'share', 'unshare')),
    activity_time   Timestamptz NOT NULL DEFAULT NOW(),
    status         varchar(255) NOT NULL CHECK ( status IN ('success', 'failure')),
    file_id         uuid        REFERENCES Cloud(file_id) ON UPDATE CASCADE
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

CREATE TABLE email_queue
(
    email_id     uuid       NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    user_account uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    email_subject varchar(255) NOT NULL,
    status      varchar(255) NOT NULL CHECK ( status IN ('pending', 'sent')),
    time_sent   Timestamptz NOT NULL DEFAULT NOW(),
    email_body  varchar(255) NOT NULL
);

CREATE TABLE share
(
    share_id     uuid       NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    user_account uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    file_id      uuid       NOT NULL REFERENCES Cloud(file_id) ON UPDATE CASCADE,
    share_type   varchar(255) NOT NULL CHECK ( share_type IN ('read', 'write')),
    share_with   uuid       NOT NULL REFERENCES user_account(uuid) ON UPDATE CASCADE,
    status       varchar(255) NOT NULL CHECK ( status IN ('active', 'inactive'))
);

CREATE TABLE security_policies (
    policy_id           uuid          NOT NULL PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    policy_name         VARCHAR(255)    NOT NULL,
    description         TEXT,
    enforcement_level   VARCHAR(50),
    policy_type         VARCHAR(50),
    parameters          JSONB,
    status              VARCHAR(50)
);





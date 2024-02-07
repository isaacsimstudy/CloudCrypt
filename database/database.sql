INSERT INTO user_profile
    (privilege, title)
VALUES
    ('customer', 'customer'),
    ('admin', 'admin'),
    ('owner', 'owner');

-- Insert default users accounts (6 customers, 3 admins, 2owners)
INSERT INTO user_account
    (password_hash, username, email, first_name, last_name, address, date_of_birth, user_profile)
VALUES
    ('password-customer-1', 'customer-1', 'cust1@cloudcrypt.com', 'customer', '1', 'address', '1991-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-2', 'customer-2', 'cust2@cloudcrypt.com', 'customer', '2', 'address', '1992-02-02', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-3', 'customer-3', 'cust3@cloudcrypt.com', 'customer', '3', 'address', '1993-03-03', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-4', 'customer-4', 'cust4@cloudcrypt.com', 'customer', '3', 'address', '1994-04-04', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-5', 'customer-5', 'cust5@cloudcrypt.com', 'customer', '3', 'address', '1995-05-05', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-6', 'customer-6', 'cust6@cloudcrypt.com', 'customer', '3', 'address', '1996-06-06', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-admin-1', 'admin-1', 'admin1@cloudcrypt.com', 'admin', '1', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'admin')),
    ('password-admin-2', 'admin-2', 'admin2@cloudcrypt.com', 'admin', '2', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'admin')),
    ('password-admin-3', 'admin-3', 'admin3@cloudcrypt.com', 'admin', '3', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'admin')),
    ('password-owner-1', 'owner-1', 'owner1@cloudcrypt.com', 'owner', '1', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'owner')),
    ('password-owner-2', 'owner-2', 'owner2@cloudcrypt.com', 'owner', '2', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'owner'));

-- Insert customer_details for each customer (customer 1-3 are premium, customer 4-6 are free)
INSERT INTO customer_details
    (uuid, sub_tier)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'premium'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'premium'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'premium'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-4'), 'free'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-5'), 'free'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-6'), 'free');

-- Insert notification_settings: (customer 1 upload, customer 2 download, customer 3 logins twice - to share/unshare separately
INSERT INTO notification_settings
    (user_account, notification_type, notification_method, status, notification_frequency)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'login', 'email', 'active', 'daily'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'upload', 'email', 'active', 'daily'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'logout', 'email', 'active', 'daily'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'login', 'email', 'active', 'weekly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'download', 'email', 'active', 'weekly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'logout', 'email', 'active', 'weekly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'login', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'share', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'logout', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'login', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'unshare', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'login', 'email', 'active', 'monthly');

-- Insert key (sample 1-9 are randomly generated and not to be used for actual encryption)
INSERT INTO key
    (uuid, name, password_hash)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'sample-1', 'G3p6N9w2R5n9w6hz8T1q7'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'sample-2', 's5Z8b3N6x1R9v4E7q2C5h8M'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'sample-3', 'X5m7H43c2B9fm1fwK6e'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'sample-4', 'W3d6H9r2T5f8K1g4J7l0S3v'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'sample-5', 'Y8n3Z6x1p5fWo27q4V9b'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'sample-6', 'j4X7p2D9m1Q6c3L8w5F2G7t'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'sample-7', 'L2k9S3j7Dfw4p6nfuE5v'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'sample-8', 'A7u0Y3o6P9i2k5V8n1B4e7U'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'sample-9', 'xJ9Kpa3c0Sv7eL8wQbz2M');

-- Insert activity_log (customer 1 upload, customer 2 download, customer 3 logins twice - to share/unshare separately)
INSERT INTO activity_log
    (user_account, activity_type, activity_time, status, file_id)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'login', '2023-12-024 21:12:47+08:00', 'failure', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'login', '2023-12-024 21:13:32+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'share', '2023-12-024 21:15:52+08:00', 'success',
     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer-3') AND file_name = 'customer-3Sharing')),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'logout', '2023-12-024 21:19:36+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'login', '2024-01-017 10:37:12+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'upload', '2024-01-017 10:39:39+08:00', 'success',
     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer-1') AND file_name = 'customer-1File')),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 'logout', '2024-01-017 10:43:53+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'login', '2024-01-019 16:58:42+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'download', '2024-01-019 16:59:37+08:00', 'success',
     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer-2') AND file_name = 'customer-2File')),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 'logout', '2024-01-019 17:08:13+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'login', '2024-01-023 12:18:54+08:00', 'success', NULL),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'unshare', '2024-01-023 12:20:21+08:00', 'success',
     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer-3') AND file_name = 'customer-3Sharing')),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'logout', '2024-01-023 12:24:36+08:00', 'success', NULL);

-- Insert login_settings (customer-3 got 2 login_attempts on 2023-12-024 due to initial failure)
INSERT INTO login_settings
    (user_account, login_attempts, login_status, login_time, two_factor_auth)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 1, 'active', '2023-12-024 21:12:47+08:00', 'active'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 1, 'active', '2023-12-024 21:13:32+08:00', 'active'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-1'), 1, 'active', '2024-01-017 10:37:12+08:00', 'active'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-2'), 1, 'active', '2024-01-019 16:58:42+08:00', 'active'),
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 1, 'active', '2024-01-023 12:18:54+08:00', 'active');

-- Insert share (customer-3 share file with customer-1
INSERT INTO share
    (user_account, share_type, share_with, status, file_id)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer-3'), 'write', (SELECT uuid FROM user_account WHERE username = 'customer-1', 'active'),
     SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer-3') AND file_name = 'customer-3Sharing');










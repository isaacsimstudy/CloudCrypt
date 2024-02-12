INSERT INTO user_profile
    (privilege, title)
VALUES
    ('customer', 'customer'),
    ('admin', 'admin'),
    ('owner', 'owner');

-- Insert default users accounts
INSERT INTO user_account
    (user_profile, password_hash, username, email, first_name, last_name, address, date_of_birth, time_created, time_last_login, phone_number)
VALUES
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash1', 'customer1', 'customer1@email.com', 'FirstName1', 'LastName1', '123 Main St, City, Country', '1990-01-01', '2024-02-08T09:00:00Z', '2024-02-08T10:00:00Z', '1234567890'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash2', 'customer2', 'customer2@email.com', 'FirstName2', 'LastName2', '456 Elm St, City, Country', '1992-02-02', '2024-02-08T09:15:00Z', '2024-02-08T10:15:00Z', '2345678901'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash3', 'customer3', 'customer3@email.com', 'FirstName3', 'LastName3', '789 Pine St, City, Country', '1994-03-03', '2024-02-08T09:30:00Z', '2024-02-08T10:30:00Z', '3456789012'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash4', 'customer4', 'customer4@email.com', 'FirstName4', 'LastName4', '1012 Oak St, City, Country', '1996-04-04', '2024-02-08T09:45:00Z', '2024-02-08T10:45:00Z', '4567890123'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash5', 'customer5', 'customer5@email.com', 'FirstName5', 'LastName5', '1234 Maple St, City, Country', '1998-05-05', '2024-02-08T10:00:00Z', '2024-02-08T11:00:00Z', '5678901234'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash6', 'customer6', 'customer6@email.com', 'FirstName6', 'LastName6', '5678 Birch St, City, Country', '2000-06-06', '2024-02-08T10:15:00Z', '2024-02-08T11:15:00Z', '6789012345'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash7', 'customer7', 'customer7@email.com', 'FirstName7', 'LastName7', '9012 Cedar St, City, Country', '2002-07-07', '2024-02-08T10:30:00Z', '2024-02-08T11:30:00Z', '7890123456'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash8', 'customer8', 'customer8@email.com', 'FirstName8', 'LastName8', '3456 Redwood St, City, Country', '2004-08-08', '2024-02-08T10:45:00Z', '2024-02-08T11:45:00Z', '8901234567'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash9', 'customer9', 'customer9@email.com', 'FirstName9', 'LastName9', '7890 Spruce St, City, Country', '2006-09-09', '2024-02-08T11:00:00Z', '2024-02-08T12:00:00Z', '9012345678'),
    ((SELECT uuid FROM user_profile WHERE title = 'customer'), 'passwordHash10', 'customer10', 'customer10@email.com', 'FirstName10', 'LastName10', '12345 Willow St, City, Country', '2008-10-10', '2024-02-08T11:15:00Z', '2024-02-08T12:15:00Z', '0123456789'),
    ((SELECT uuid FROM user_profile WHERE title = 'admin'), 'adminHash1', 'admin1', 'admin1@email.com', 'AdminFirstName1', 'AdminLastName1', 'Admin Address 1, City, Country', '1985-01-01', '2024-02-08T09:00:00Z', '2024-02-08T10:00:00Z', '1122334455'),
    ((SELECT uuid FROM user_profile WHERE title = 'admin'), 'adminHash2', 'admin2', 'admin2@email.com', 'AdminFirstName2', 'AdminLastName2', 'Admin Address 2, City, Country', '1987-02-02', '2024-02-08T09:15:00Z', '2024-02-08T10:15:00Z', '2233445566'),
    ((SELECT uuid FROM user_profile WHERE title = 'admin'), 'adminHash3', 'admin3', 'admin3@email.com', 'AdminFirstName3', 'AdminLastName3', 'Admin Address 3, City, Country', '1989-03-03', '2024-02-08T09:30:00Z', '2024-02-08T10:30:00Z', '3344556677'),
    ((SELECT uuid FROM user_profile WHERE title = 'admin'), 'adminHash4', 'admin4', 'admin4@email.com', 'AdminFirstName4', 'AdminLastName4', 'Admin Address 4, City, Country', '1991-04-04', '2024-02-08T09:45:00Z', '2024-02-08T10:45:00Z', '4455667788'),
    ((SELECT uuid FROM user_profile WHERE title = 'owner'), 'ownerHash1', 'owner1', 'owner1@email.com', 'OwnerFirstName', 'OwnerLastName', 'Owner Address, City, Country', '1980-05-05', '2024-02-08T10:00:00Z', '2024-02-08T11:00:00Z', '5566778899');

-- Insert customer_details for each customer (customer 1-3 are premium, customer 4-6 are free)
INSERT INTO customer_details
    (user_account, sub_tier)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'premium'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'premium'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'premium'),
    ((SELECT uuid FROM user_account WHERE username = 'customer4'), 'free'),
    ((SELECT uuid FROM user_account WHERE username = 'customer5'), 'free'),
    ((SELECT uuid FROM user_account WHERE username = 'customer6'), 'free');

-- Insert notification_settings: (customer 1 upload, customer 2 download, customer 3 logins twice - to share/unshare separately
INSERT INTO notification_settings
    (user_account, notification_type, notification_method, status, notification_frequency)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'login', 'email', 'active', 'daily'),
    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'upload', 'email', 'active', 'daily'),
    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'logout', 'email', 'active', 'daily'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'login', 'email', 'active', 'weekly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'download', 'email', 'active', 'weekly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'logout', 'email', 'active', 'weekly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'login', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'share', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'logout', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer4'), 'login', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer4'), 'unshare', 'email', 'active', 'monthly'),
    ((SELECT uuid FROM user_account WHERE username = 'customer4'), 'login', 'email', 'active', 'monthly');

-- Insert key (sample 1-9 are randomly generated and not to be used for actual encryption)
INSERT INTO key
    (uuid, name, password_hash)
VALUES
    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'sample-1', 'G3p6N9w2R5n9w6hz8T1q7'),
    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'sample-2', 's5Z8b3N6x1R9v4E7q2C5h8M'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'sample-3', 'X5m7H43c2B9fm1fwK6e'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'sample-4', 'W3d6H9r2T5f8K1g4J7l0S3v'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'sample-5', 'Y8n3Z6x1p5fWo27q4V9b'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'sample-6', 'j4X7p2D9m1Q6c3L8w5F2G7t'),
    ((SELECT uuid FROM user_account WHERE username = 'customer4'), 'sample-7', 'L2k9S3j7Dfw4p6nfuE5v'),
    ((SELECT uuid FROM user_account WHERE username = 'customer4'), 'sample-8', 'A7u0Y3o6P9i2k5V8n1B4e7U'),
    ((SELECT uuid FROM user_account WHERE username = 'customer5'), 'sample-9', 'xJ9Kpa3c0Sv7eL8wQbz2M');

-- Insert activity_log (customer 1 upload, customer 2 download, customer 3 logins twice - to share/unshare separately)
--INSERT INTO activity_log
--    (user_account, activity_type, activity_time, status, file_id)
--VALUES
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'login', '2023-12-024 21:12:47+08:00', 'failure', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'login', '2023-12-024 21:13:32+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'share', '2023-12-024 21:15:52+08:00', 'success',
--     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer3') AND file_name = 'customer-3Sharing')),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'logout', '2023-12-024 21:19:36+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'login', '2024-01-017 10:37:12+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'upload', '2024-01-017 10:39:39+08:00', 'success',
--     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer1') AND file_name = 'customer-1File')),
--    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'logout', '2024-01-017 10:43:53+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'login', '2024-01-019 16:58:42+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'download', '2024-01-019 16:59:37+08:00', 'success',
--     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer2') AND file_name = 'customer-2File')),
--    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'logout', '2024-01-019 17:08:13+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'login', '2024-01-023 12:18:54+08:00', 'success', NULL),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'unshare', '2024-01-023 12:20:21+08:00', 'success',
--     (SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer3') AND file_name = 'customer-3Sharing')),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'logout', '2024-01-023 12:24:36+08:00', 'success', NULL);

-- Insert login_settings (customer-3 got 2 login_attempts on 2023-12-024 due to initial failure)
INSERT INTO login_settings
    (user_account, login_attempts, login_status, login_time, two_factor_auth)
VALUES
    --((SELECT uuid FROM user_account WHERE username = 'customer3'), 1, 'active', '2023-12-024 21:12:47+08:00', 'active'),
    --((SELECT uuid FROM user_account WHERE username = 'customer3'), 1, 'active', '2023-12-024 21:13:32+08:00', 'active'),
    --((SELECT uuid FROM user_account WHERE username = 'customer1'), 1, 'active', '2024-01-017 10:37:12+08:00', 'active'),
    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 1, 'active', '2024-01-019 16:58:42+08:00', 'active'),
    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 1, 'active', '2024-01-023 12:18:54+08:00', 'active');

-- Insert share (customer-3 share file with customer-1
--INSERT INTO share
--    (user_account, share_type, share_with, status, file_id)
--VALUES
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'write', (SELECT uuid FROM user_account WHERE username = 'customer1', 'active'),
--     SELECT uuid FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer3') AND file_name = 'customer3Sharing');

-- Insert cloud (checksum generated are samples and not to be used for actual encryption, encrypted_file is just a string-to-byte conversion of customer file name)
--INSERT INTO cloud
--    (user_account, file_name, encrypted_file, key_id, status, checksum)
--VALUES
--    ((SELECT uuid FROM user_account WHERE username = 'customer1'), 'customer-1File', E'\\x637573746F6D65722D3146696C65',
--     (SELECT key_id FROM key WHERE uuid = (SELECT uuid FROM user_account WHERE username = 'customer1') AND name = 'sample-1'), 'active', '6b5e47b53957a1e9c7a3bc224a0b28f3e9e0d0e8f7b3a9c40b3c3d4e5f6a7b8c'),
--    ((SELECT uuid FROM user_account WHERE username = 'customer2'), 'customer-2File', E'\\x637573746F6D65722D3246696C65',
--     (SELECT key_id FROM key WHERE uuid = (SELECT uuid FROM user_account WHERE username = 'customer2') AND name = 'sample-3'), 'active', '8c7b6a5f4e3d2c1b0a9f8e7d6c5b4a3a2b1c0d9e8f7g6h5i4j3k2l1m0n9o8p7q6r5s4t3u2v1w0x9y8z7'),
--    ((SELECT uuid FROM user_account WHERE username = 'customer3'), 'customer-3Sharing', E'\\x637573746F6D65722D3353686172696E67',
--     (SELECT key_id FROM key WHERE uuid = (SELECT uuid FROM user_account WHERE username = 'customer3') AND name = 'sample-5'), 'active', '7b6e5d4c3b2a1f0e9d8c7b6a5f4e3d2c1b0a9f8e7d6c5b4a3');

-- Insert file_info (currently only have 3 files)
--INSERT INTO file_info
--    (file_id, user_account, original_size, file_type, original_hash, time_uploaded, last_modified, file_owner, encryption_type, tags)
--VALUES
--    ((SELECT file_id FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer1') AND file_name = 'customer-1File'), (SELECT uuid FROM user_account WHERE username = 'customer1'),
--     1024, 'docx', '6b5e47b53957a1e9c7a3bc224a0b28f3e9e0d0e8f7b3a9c40b3c3d4e5f6a7b8c', '2024-01-017 10:39:39+08:00', '2024-01-017 10:39:39+08:00', 'customer1', 'AES-256', 'customer1, file, docx'),
--    ((SELECT file_id FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer2') AND file_name = 'customer-2File'), (SELECT uuid FROM user_account WHERE username = 'customer2'),
--     2048, 'pdf', '8c7b6a5f4e3d2c1b0a9f8e7d6c5b4a3a2b1c0d9e8f7g6h5i4j3k2l1m0n9o8p7q6r5s4t3u2v1w0x9y8z7', '2024-01-019 16:59:37+08:00', '2024-01-019 16:59:37+08:00', 'customer2', 'AES-256', 'customer2, file, pdf'),
--    ((SELECT file_id FROM cloud WHERE user_account = (SELECT uuid from user_account WHERE username = 'customer3') AND file_name = 'customer-3Sharing'), (SELECT uuid FROM user_account WHERE username = 'customer3'),
--     4096, 'ppt', '7b6e5d4c3b2a1f0e9d8c7b6a5f4e3d2c1b0a9f8e7d6c5b4a3', '2024-01-023 12:20:21+08:00', '2024-01-023 12:20:21+08:00', 'customer3', 'AES-256', 'customer3, file, ppt');

-- Insert email_queue (following sequence of events from activity_log)
-- INTO email_queue
--    (user_account, email_subject, status, time_sent, email_body)
--VALUES
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Login Notification', 'sent', '2023-12-024 21:12:47+08:00', 'Login attempt failed'),
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Login Notification', 'sent', '2023-12-024 21:13:32+08:00', 'Login attempt successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Share Notification', 'sent', '2023-12-024 21:15:52+08:00', 'File shared successfully'),
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Logout Notification', 'sent', '2023-12-024 21:19:36+08:00', 'Logout successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer1'), 'Login Notification', 'sent', '2024-01-017 10:37:12+08:00', 'Login successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer1'), 'Upload Notification', 'sent', '2024-01-017 10:39:39+08:00', 'File uploaded successfully'),
--    ((SELECT uuid from user_account WHERE username = 'customer1'), 'Logout Notification', 'sent', '2024-01-017 10:43:53+08:00', 'Logout successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer2'), 'Login Notification', 'sent', '2024-01-019 16:58:42+08:00', 'Login successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer2'), 'Download Notification', 'sent', '2024-01-019 16:59:37+08:00', 'File downloaded successfully'),
--    ((SELECT uuid from user_account WHERE username = 'customer2'), 'Logout Notification', 'sent', '2024-01-019 17:08:13+08:00', 'Logout successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Login Notification', 'sent', '2024-01-023 12:18:54+08:00', 'Login successful'),
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Unshare Notification', 'sent', '2024-01-023 12:20:21+08:00', 'File unshared successfully'),
--    ((SELECT uuid from user_account WHERE username = 'customer3'), 'Logout Notification', 'sent', '2024-01-023 12:24:36+08:00', 'Logout successful');

-- Insert security_policies
--INSERT INTO security_policies
--    (policy_name, description, enforcement_level, policy_type, parameters, status)
--VALUES
--    (varchar(255), TEXT, VARCHAR(50), VARCHAR(50), JSONB, VARCHAR(50));


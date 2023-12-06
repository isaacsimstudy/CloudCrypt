INSERT INTO user_profile
    (privilege, title)
VALUES
    ('customer', 'customer'),
    ('admin', 'admin'),
    ('owner', 'owner');

-- Insert default users accounts: 3 customers, 3 admins, 2owners
INSERT INTO user_account
    (password_hash, username, email, first_name, last_name, address, date_of_birth, user_profile)
VALUES
    ('password-customer-1', 'customer-1', 'cust1@cloudcrypt.com', 'customer', '1', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-2', 'customer-2', 'cust2@cloudcrypt.com', 'customer', '2', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-customer-3', 'customer-3', 'cust3@cloudcrypt.com', 'customer', '3', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'customer')),
    ('password-admin-1', 'admin-1', 'admin1@cloudcrypt.com', 'admin', '1', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'admin')),
    ('password-admin-2', 'admin-2', 'admin2@cloudcrypt.com', 'admin', '2', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'admin')),
    ('password-admin-3', 'admin-3', 'admin3@cloudcrypt.com', 'admin', '3', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'admin')),
    ('password-owner-1', 'owner-1', 'owner1@cloudcrypt.com', 'owner', '1', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'owner')),
    ('password-owner-2', 'owner-2', 'owner2@cloudcrypt.com', 'owner', '2', 'address', '1990-01-01', (SELECT uuid FROM user_profile WHERE privilege = 'owner'));
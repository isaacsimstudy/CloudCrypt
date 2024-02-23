#!/bin/sh

#
# 1) Set up a fresh Ubuntu 22.04 LTS instance.
# 2) Copy database/*.sql files into the instance home directory.
# 3) Run this script.
#

# Install the necessary software.
sudo apt update -y && sudo apt upgrade -y
sudo apt install -y openjdk-17-jdk openjdk-17-jre-headless default-jre


wget -qO- https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ $(lsb_release -cs)-pgdg main" >> /etc/apt/sources.list.d/pgdg.list'
sudo apt update -y && sudo apt upgrade -y
sudo apt install -y postgresql postgresql-contrib
sudo service postgresql status
sudo systemctl enable --now postgresql.service

# Create the necessary folders.
mkdir -p ~/bin ~/CloudCrypt

# Deploy the database.
mv ~/*.sql ~/ctbs
sudo -u postgres psql -f ~/CloudCrypt/schema.sql
sudo -u postgres psql -f ~/CloudCrypt/database.sql

# Create a service "CloudCrypt-app" that will run the backend application.
sudo tee /etc/systemd/system/CloudCrypt-app.service <<EOF
[Unit]
Description=My SpringBootApp Java REST Service
[Service]
User=ubuntu

# The configuration file application.properties should be here:
# Change this to your workspace.
WorkingDirectory=/home/ubuntu/CloudCrypt

# Path to executable, points straight to the jar file:
ExecStart=/usr/bin/java -jar CloudCrypt-1.0.0-SNAPSHOT.jar

SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target

EOF

sudo systemctl daemon-reload
sudo systemctl enable --now CloudCrypt-app
sudo systemctl status CloudCrypt-app
#!/bin/sh

#
# This script is executed on the server,
# each time a new version of the application is deployed.
#

# Create bin folder if it does not exist.
test -d ~/bin || mkdir ~/bin

# Move backend application to bin folder.
if test -f ~/bin/Cloud-1.0.0-SNAPSHOT.jar; then
rm -f ~/bin/Cloud-1.0.0-SNAPSHOT.jar
cp -f ~/CloudCrypt/CloudCrypt-1.0.0-SNAPSHOT.jar ~/bin
rm -f ~/CloudCrypt/CloudCrypt-1.0.0-SNAPSHOT.jar
else
printf "%s: %s\n" "No Cloud-1.0.0-SNAPSHOT.jar file found" "$(date)" >> ~/CloudCrypt/log.txt
fi

sudo systemctl restart CloudCrypt-app
exit 0
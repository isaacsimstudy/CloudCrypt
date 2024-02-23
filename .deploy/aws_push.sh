#!/bin/sh

#
# This script is executed on the server,
# each time a new version of the application is deployed.
#

# Create bin folder if it does not exist.
test -d ~/bin || mkdir ~/bin

# Move backend application to bin folder.
if test -f ~/bin/CloudCrypt-1.0.0-SNAPSHOT.jar.original; then
rm -f ~/bin/CloudCrypt-1.0.0-SNAPSHOT.jar.original
cp -f ~/CloudCrypt/CloudCrypt-1.0.0-SNAPSHOT.jar.original ~/bin
rm -f ~/CloudCrypt/CloudCrypt-1.0.0-SNAPSHOT.jar.original
else
printf "%s: %s\n" "No Cloud-1.0.0-SNAPSHOT.jar file found" "$(date)" >> ~/CloudCrypt/log.txt
fi

sudo systemctl restart CloudCrypt-app
exit 0
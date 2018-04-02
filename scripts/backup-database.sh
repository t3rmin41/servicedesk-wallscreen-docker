#!/bin/sh
BUILD_ID=dontKillMe
#echo Setting JAVA_OPTS to use IPv4
JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses"
ENV=$1
DB_DIR=./db/$ENV
DB_BACKUP_DIR=./db-backup/$ENV
NOW=$(date +"%Y-%m-%d_%H-%M")
#Create backup directory if not exists
mkdir -p $DB_BACKUP_DIR

if [ -z "$1" ];
  then
    echo "No environment supplied"
    exit 1
  else
    for file in $(ls $DB_DIR); do
      BACKUP_FILENAME=$NOW.$file
      echo "Will copy now $BACKUP_FILENAME as backup file"
      cp $DB_DIR/$file $DB_BACKUP_DIR/$BACKUP_FILENAME
    done
fi

#Do the cleanup - delete files older than 14400 minutes = 10 days
find $DB_BACKUP_DIR/ -type f -name '*' -mmin +14400 -exec rm {} \;

exit 0

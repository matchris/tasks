#!/bin/bash

stop_tomcat()
{
  systemctl stop tomcat
}

start_tomcat()
{
  systemctl start tomcat
  end
}

rename() {
  sudo rm build/libs/crud.war
  if sudo mv build/libs/tasks-0.0.1-SNAPSHOT.war build/libs/crud.war; then
     echo "Successfully renamed file"
  else
     echo "Cannot rename file"
     fail
  fi
}

copy_file() {
  if cp build/libs/crud.war /opt/tomcat/webapps; then
     start_tomcat
  else
     fail
  fi
}

fail() {
  echo "There were errors"
}

end() {
  echo "Work is finished"
}

if ./gradlew build; then
   rename
   copy_file

else
   stop_tomcat
   fail
fi
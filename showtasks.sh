#!/bin/bash

start_chromium()
{
/usr/bin/chromium-browser http://localhost:8888/tasks_frontend
}

run_script(){
  sudo -u root ./runcrud.sh
}

fail() {
  echo "There were errors"
}

end() {
  echo "Work is finished"
}

if run_script; then
   start_chromium
   end
else
   fail
fi
#!/bin/bash
echo "INIT ///////////////////////////////////////////////"
mongo languagenote

db.createUser(
{ user: "languagenoteadmin",
    pwd: "password",
	roles:
       [
         { role: "readWrite", db: "config" },
         "dbAdmin", "dbOwner","userAdmin"
       ]
});


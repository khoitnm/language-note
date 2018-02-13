# BACKUP
http://stackoverflow.com/questions/25567744/backup-neo4j-community-edition-offline-in-unix-mac-or-linux

## Basic:
Online backup, in a sense of taking a consistent backup while Neo4j is running, is only available in Neo4j enterprise edition.
Enterprise edition's backup also features a verbose consistency check of the backup, something you do not get in community either.
The only safe option in community edition is to shutdown Neo4j cleanly and copy away the graph.db folder recursively. I'm typically using:

```
cd data
tar -zcf graph.db.tar.gz graph.db/
```

to restore:
```
cd data
rm -rf graph.db
tar -zxf graph.db.tar.gz
```

NOTE:
To add on to this, make sure you chmod and chown the graph.db folder correctly after untarring it.
I was running into permission issues (scared the hell out of me actually since it was complaining about the indexes being in a failed state)

You can also do a rsync -a ..... This will map the ownership to the user running rsync. If it's the neo4j user on the target machine, you should be fine.


neo4j-admin backup --backup-dir=/SoftRun/Neo4j/bck --name=languagenote --from=localhost:7474 --fallback-to-full=true --check-consistency=true --cc-report-dir=/SoftRun/Neo4j/bck/languagenote-report --timeout=30m



## Script
I also ran into this issue and wrote following two codes:

Make backup of momentary state

```
service neo4j stop && now=$(date +"%m_%d_%Y") && cd /var/lib/neo4j/data/databases/ && tar -cvzf /var/backups/neo4j/$now.gb.tar.gz graph.db && service neo4j start
```

service neo4j stop = stop the neo4j service
now=$(date +"%m_%d_%Y") = declare the current date as variable
cd /var/lib/neo4j/data/databases/ = change directories to your neo4j dir where the graph.db is located
tar -cvzf /var/backups/neo4j/$now.gb.tar.gz graph.db = make a compressed copy of the graph.db and save it to /var/backups/neo4j/$now.gb.tar.gz
service neo4j start = restart neo4j
Restore neo4j database from a backup

```
service neo4j stop && cd /var/lib/neo4j/data/databases/ && rm -r graph.db && tar xf /var/backups/neo4j/10_25_2016.gb.tar.gz -C /var/lib/neo4j/data/databases/ && service neo4j start
```

service neo4j stop = stop the neo4j service
cd /var/lib/neo4j/data/databases/ = change directories to your neo4j dir where the graph.db is located
rm -r graph.db = remove the current graph.db and all its contents
tar xf /var/backups/neo4j/10_25_2016.gb.tar.gz -C /var/lib/neo4j/data/databases/ = Extract the backup to the directory where the old graph.db was located. Be sure to adjust the filename 10_25_2016.gb.tar.gz to what you called your file
service neo4j start = restart neo4j
Info: This seems to work for me but as I do not have alot of experience with bash scripting I doubt this is the optimal way. But I think it is understandable and easy to customize :)

Cheers
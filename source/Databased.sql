/* Using sqlite3, check source for csv files used. */

.mode csv

CREATE TABLE entity(
    entityID integer,
    type varchar(20),
    primary key(entityID)
);
.import Entity.csv entity
    

CREATE TABLE inGame(
    inGameID integer,
    entityID integer,
    customName varchar(40),
    posX float,
    posY float,
    posZ float,
    dimension varchar(40),
    FOREIGN KEY(entityID) REFERENCES entity(entityID),
    primary key(inGameID)
);
.import InGame.csv inGame


CREATE TABLE living(
    livingID integer,
    inGameID integer,
    health float,"
    attackDamage float,
    burnsInLight bit,
    hostility varchar(20),
    FOREIGN KEY(inGameID) REFERENCES inGame(inGameID),
    primary key(livingID)
);
.import Living.csv living

db = db.getSiblingDB('moviesdb');
db.createUser(
    {
        user: "root",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "moviesdb"
            }
        ]
    }
);

db = db.getSiblingDB('usersdb');
db.createUser(
    {
        user: "root",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "usersdb"
            }
        ]
    }
);
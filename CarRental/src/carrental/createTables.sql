CREATE TABLE "CUSTOMERS" (
                "ID" bigint primary key generated always as identity,
                "PHONE" varchar(255),
                "FIRSTNAME" varchar(255),
                "LASTNAME" varchar(255));

CREATE TABLE "CARS" (
                "ID" bigint primary key generated always as identity,
                "NUMBERPLATE" varchar(255),
                "PRICEPERDAY" NUMERIC);

CREATE TABLE "LEASES" (
                "ID" bigint primary key generated always as identity,
                "FROMDATE" Date,
                "TODATE" Date,
                "REALRETURN" Date,
                "CUSTOMER" bigint,
                "CAR" bigint);
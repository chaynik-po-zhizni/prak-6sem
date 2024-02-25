DROP TABLE IF EXISTS Cars, Brands, Models, Orders, Clients;
DROP TYPE IF EXISTS drive_type, status_type, transmission_type, fuel_type;

SET DateStyle TO 'German', 'DMY';

CREATE TYPE drive_type AS ENUM ('front', 'rear', 'four');
CREATE TYPE status_type AS ENUM ('issued', 'in_progress', 'done', 'canceled');
CREATE TYPE transmission_type AS ENUM ('MT', 'AT', 'CVT', 'Robot');
CREATE TYPE fuel_type AS ENUM ('gas','diesel', 'electricity', 'gasoline');


CREATE TABLE Brands (
	brand_id serial PRIMARY KEY,
	name varchar(255) NOT NULL
);

CREATE TABLE Models (
	model_id serial PRIMARY KEY,
	brand_id integer REFERENCES Brands (brand_id) ON DELETE CASCADE ON UPDATE CASCADE,
	name varchar(255) NOT NULL
);

CREATE TABLE Cars (
	car_id serial PRIMARY KEY,
	model_id integer REFERENCES Models (model_id) ON DELETE SET NULL ON UPDATE CASCADE,
	configuration jsonb,
	left_steering_wheel boolean NOT NULL DEFAULT TRUE,
	mileage integer,
	price integer NOT NULL,
	transmission transmission_type NOT NULL DEFAULT 'MT',
	drive drive_type NOT NULL DEFAULT 'front',
	fuel fuel_type NOT NULL DEFAULT 'gasoline'
);

CREATE TABLE Clients (
	client_id serial PRIMARY KEY,
	email varchar(130),
	address text,
	phone bigint NOT NULL,
	full_name text NOT NULL,
	UNIQUE (phone)
);

CREATE TABLE Orders (
	order_id serial PRIMARY KEY,
	client_id integer REFERENCES Clients (client_id) ON DELETE CASCADE ON UPDATE CASCADE,
	"date" timestamp with time zone NOT NULL,
	status status_type NOT NULL DEFAULT 'issued',
	car_id integer REFERENCES Cars (car_id) ON DELETE CASCADE ON UPDATE CASCADE,
	test_drive boolean NOT NULL DEFAULT FALSE
);

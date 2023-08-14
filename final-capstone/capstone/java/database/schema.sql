BEGIN TRANSACTION;

DROP TABLE IF EXISTS reviewed_landmark, shared_trips, trips, destinations, users, landmarks, itinerary CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);




CREATE TABLE landmarks (
	landmark_id SERIAL,
	landmark_name varchar(100) NOT NULL,
	landmark_photo varchar(1000) NOT NULL,
	landmark_description varchar(5000) NOT NULL,
	landmark_address varchar(200) NOT NULL,
	landmark_hours_of_operation varchar NOT NULL,
	landmark_venue_type varchar(100) NOT NULL,
	landmark_like_count INT NOT NULL DEFAULT 0,
	landmark_dislike_count INT NOT NULL DEFAULT 0,
	CONSTRAINT PK_landmarks PRIMARY KEY (landmark_id)
);

CREATE TABLE itinerary (
	itinerary_id SERIAL,
    itinerary_name varchar(50),
    itinerary_date date NOT NULL,
    itinerary_starting_point varchar(100) NOT NULL,
	CONSTRAINT PK_itinerary PRIMARY KEY (itinerary_id)
);

CREATE TABLE trips (
    trip_id SERIAL,
    user_id INT NOT NULL,
    itinerary_id INT NOT NULL,
    CONSTRAINT PK_trips PRIMARY KEY (trip_id, user_id, itinerary_id),
    CONSTRAINT FK_trip_user FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT FK_trip_itinerary FOREIGN KEY(itinerary_id) REFERENCES itinerary(itinerary_id)
);

CREATE TABLE shared_trips (
    trip_id INT ,
    user1_id INT ,
    user2_id INT
);

CREATE TABLE destinations (
    itinerary_id INT NOT NULL,
    landmark_id INT NOT NULL,
    CONSTRAINT PK_destinations PRIMARY KEY (itinerary_id, landmark_id),
    CONSTRAINT FK_destination_itinerary FOREIGN KEY (itinerary_id) REFERENCES itinerary(itinerary_id),
    CONSTRAINT FK_destination_landmark FOREIGN KEY (landmark_id) REFERENCES landmarks(landmark_id)
);

CREATE TABLE reviewed_landmark (
    user_id int,
    landmark_id int,
    reviewed BOOLEAN,
    CONSTRAINT PK_reviewed_landmark PRIMARY KEY (user_id, landmark_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_landmark FOREIGN KEY (landmark_id) REFERENCES landmarks(landmark_id)
);

COMMIT TRANSACTION;

SELECT * FROM landmarks;
SELECT * FROM users;
SELECT * FROM itinerary;




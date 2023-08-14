BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


INSERT INTO landmarks (landmark_name, landmark_photo, landmark_description, landmark_address, landmark_hours_of_operation, landmark_venue_type)
VALUES ('Liberty Bell', 'https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=AIzaSyD-FoV_A8s-yXMebNAb6ulZO3kvd6dY8tg&photo_reference=AZose0lhcr8nir-K9oxrXek4fwUMzFXpfqGqnvQ7F9O8SYsc3LETcC92BoHh8GHs4CzcdBrNwREedPDuUwkt_REK3OzxjuBkuoISnzCJIpuUwDQcnXRuvmC19ABxkJ7WbNbL5ftt1UN2KCteVCZYWyjAaRqELIzawWu5aUDB_FeftQKN91-4', 'Last rung in 1846, this 2,080-pound icon of freedom features a biblical inscription & a famed crack.', '526 Market St, Philadelphia, PA 19106, United States', 'Monday: 9:00 AM – 5:00 PM, Tuesday: 9:00 AM – 5:00 PM, Wednesday: 9:00 AM – 5:00 PM, Thursday: 9:00 AM – 5:00 PM, Friday: 9:00 AM – 5:00 PM, Saturday: 9:00 AM – 7:00 PM, Sunday: 9:00 AM – 7:00 PM', '"tourist_attraction"');

INSERT INTO landmarks (landmark_name, landmark_photo, landmark_description, landmark_address, landmark_hours_of_operation, landmark_venue_type)
VALUES ('Independence Hall','https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=AIzaSyD-FoV_A8s-yXMebNAb6ulZO3kvd6dY8tg&photo_reference=AZose0lXnzMASoYC4GKaQNvA5-VzZfnpZy-w4TZ_tEvF9Z3iCU_tSEKlLfvuIp_1kGk_tyOv7KILTmH9zQ9ovbH-LZZbz7ukZPCeqIo0w2gArnfXGk7Sof1E0ji5QPTms1cf_YvDVzvekwXIxmA3ljGS3SbiQGAWnm75geAEGqfp756L4qyv','Georgian hall where the Declaration of Independence & the U.S. Constitution were debated & signed.','520 Chestnut St, Philadelphia, PA 19106, United States','Monday: 9:00 AM – 5:00 PM, Tuesday: 9:00 AM – 5:00 PM, Wednesday: 9:00 AM – 5:00 PM, Thursday: 9:00 AM – 5:00 PM, Friday: 9:00 AM – 5:00 PM, Saturday: 9:00 AM – 7:00 PM, Sunday: 9:00 AM – 7:00 PM','point_of_interest');

INSERT INTO landmarks (landmark_name, landmark_photo, landmark_description, landmark_address, landmark_hours_of_operation, landmark_venue_type)
VALUES ('Betsy Ross House','https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=AIzaSyD-FoV_A8s-yXMebNAb6ulZO3kvd6dY8tg&photo_reference=AZose0mdZuADGY4-QoEUDbukstN0Pu_6xdp5G3GYR4ceJ4nye-ZpDSrWj5xwJ4I34dsGcCGfoJoBVNOkS0XKphuglNnbUeqEX0uGu4TQjfo7vBBJb807F_dT3My8mqUNG1nHfYO6ETYuIBmCaR48437BbyW0RTmidI7exp_hcN4gVRHtFFf5','This landmark was the home of Betsy Ross, the seamstress who sewed the first American flag in 1776.','239 Arch St, Philadelphia, PA 19106, United States','Monday: 10:00 AM – 5:00 PM, Tuesday: 10:00 AM – 5:00 PM, Wednesday: 10:00 AM – 5:00 PM, Thursday: 10:00 AM – 5:00 PM, Friday: 10:00 AM – 5:00 PM, Saturday: 10:00 AM – 5:00 PM, Sunday: 10:00 AM – 5:00 PM','tourist_attraction');

INSERT INTO landmarks (landmark_name, landmark_photo, landmark_description, landmark_address, landmark_hours_of_operation, landmark_venue_type)
VALUES ('Love Park','https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&key=AIzaSyD-FoV_A8s-yXMebNAb6ulZO3kvd6dY8tg&photo_reference=AZose0luWfqj5qlw0Wa5pqUIYGS5Fi-F_OvoF1U1Pd3KV0amdhKby-XEOF-l3qhuRsqYdLEwiNwsoscayNhysk4oSrEmvpdjGMZ2cdUpFkJf1wwdv-VKDoF7AP92dJFsLbkU2cdFKoD0U6W3wG_6ZQKtUqeLuX1Motem13ulyk46WQRUMDTj','Bustling urban plaza featuring a geyser-like fountain & Robert Indiana''s well-known Love sculpture.','Arch St, Philadelphia, PA 19102, United States','Monday: 7:00 AM – 10:00 PM, Tuesday: 7:00 AM – 10:00 PM, Wednesday: 7:00 AM – 10:00 PM, Thursday: 7:00 AM – 10:00 PM, Friday: 7:00 AM – 10:00 PM, Saturday: 7:00 AM – 10:00 PM, Sunday: 7:00 AM – 10:00 PM','park');

INSERT INTO itinerary (itinerary_name, itinerary_date, itinerary_starting_point) VALUES ('Philly Trip', '2023-06-09', '1400 John F Kennedy Blvd, Philadelphia, PA 19107');




COMMIT TRANSACTION;

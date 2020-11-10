-- noinspection SqlNoDataSourceInspectionForFile

MERGE INTO person (id, name) VALUES
    (1, 'Ryan'),
    (2, 'Becca'),
    (3, 'Jesse'),
    (4, 'Heather'),
    (5, 'Mike'),
    (6, 'Deb'),
    (7, 'Jeremy'),
    (8, 'Mary'),
    (9, 'Kaylee'),
    (10, 'Justice'),
    (11, 'Brian'),
    (12, 'Emily'),
    (13, 'Harper'),
    (14, 'Landry'),
    (15, 'Phinley'),
    (16, 'Michael'),
    (17, 'Jessica'),
    (18, 'Channing'),
    (19, 'Pearson'),
    (20, 'James'),
    (21, 'Andy'),
    (22, 'Lorena'),
    (23, 'Baby-Jones'),
    (24, 'Brett'),
    (25, 'Hannah'),
    (26, 'Scott'),
    (27, 'Robin'),
    (28, 'Ron'),
    (29, 'Laura'),
    (30, 'Kris'),
    (31, 'Lyon');


MERGE INTO family (id, family_name) VALUES
    (1, 'Levell'),
    (2, 'JonesWatkins');


MERGE INTO family_person (id, family_id, person_id ) VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 1, 3),
    (4, 1, 4),
    (5, 1, 5),
    (6, 1, 6),
    (7, 2, 7),
    (8, 2, 8),
    (9, 2, 9),
    (10, 2, 10),
    (11, 2, 11),
    (12, 2, 12),
    (13, 2, 13),
    (14, 2, 14),
    (15, 2, 15),
    (16, 2, 16),
    (17, 2, 17),
    (18, 2, 18),
    (19, 2, 19),
    (20, 2, 20),
    (21, 2, 21),
    (22, 2, 22),
    (23, 2, 23),
    (24, 2, 24),
    (25, 2, 25),
    (26, 2, 26),
    (27, 2, 27),
    (28, 2, 28),
    (29, 2, 29),
    (30, 2, 30),
    (31, 2, 31),
    (32, 2, 1),
    (33, 2, 2);



MERGE into gift_person (id, person_owner_id, person_admin_id, family_id) VALUES
     (1, 1, 1, 1),
     (2, 2, 2, 1),
     (3, 3, 3, 1),
     (4, 4, 4, 1),
     (5, 5, 5, 1),
     (6, 6, 6, 1),
     (7, 10, 7, 2),
     (8, 10, 8, 2),
     (9, 10, 10, 2),
     (11, 13, 11, 2),
     (12, 13, 12, 2),
     (13, 14, 11, 2),
     (14, 14, 12, 2),
     (15, 15, 11, 2),
     (16, 15, 12, 2),
     (17, 18, 16, 2),
     (18, 18, 17, 2),
     (19, 19, 16, 2),
     (20, 19, 17, 2),
     (21, 23, 22, 2),
     (22, 23, 21, 2),
     (23, 31, 30, 2),
    (24, 13, 13, 2),
    (25, 14, 14, 2),
    (26, 15, 15, 2),
    (27, 18, 18, 2);


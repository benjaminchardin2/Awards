INSERT INTO awd_ceremony (id, name, has_nominees, picture_url, is_highlighted, short_name, description, ceremony_date, avatar_url) VALUES (2, 'Cérémonie des Oscars 2024', true, 'https://s3.eu-central-1.amazonaws.com/centaur-wp/designweek/prod/content/uploads/2013/12/86Oscars_Logo-1002x326.jpg', true, 'Les Oscars', E'Faites vos pronostics pour la 96ème cérémonie des Oscars.
Ou bien créer votre propre palmarès pour cette cérémonie !
', '2024-03-11 02:00:00', 'https://files.offi.fr/original/article/163/b348325dc8424b9efceb874b69a71d80.jpg');


INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (25, 'Meilleur film', 2, 'MOVIE');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (26, 'Meilleure réalisateur', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (27, 'Meilleure actrice', 2, 'CAST');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (28, 'Meilleur acteur', 2, 'CAST');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (29, 'Meilleure actrice dans un second rôle', 2, 'CAST');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (30, 'Meilleur acteur dans un second rôle', 2, 'CAST');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (31, 'Meilleur scénario original', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (32, 'Meilleure scénario adapté', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (33, 'Meilleurs décors', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (34, 'Meilleure création de costumes', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (35, 'Meilleurs maquillages et coiffures', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (36, 'Meilleure photographie', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (37, 'Meilleur montage', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (38, 'Meilleur son', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (39, 'Meilleurs effets visuels', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (40, 'Meilleure chanson originale', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (41, 'Meilleure musique de film', 2, 'CREW');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (42, 'Meilleur film international', 2, 'MOVIE');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (43, E'Meilleur film d\'animation', 2, 'MOVIE');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (44, 'Meilleur film documentaire', 2, 'MOVIE');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (45, 'Meilleur court-métrage de fiction', 2, 'MOVIE');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (46, 'Meilleur court-métrage documentaire', 2, 'MOVIE');
INSERT INTO awd_award (id, name, ceremony_id, type) VALUES (47, E'Meilleur court-métrage d\'animation', 2, 'MOVIE');

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (112, 1056360, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (113, 915935, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (114, 346698, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (115, 840430, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (116, 466420, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (117, 523607, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (118, 872585, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (119, 666277, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (120, 792307, null, 25, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (121, 467244, null, 25, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (122, 915935, 1175620, 26, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (123, 466420, 1032, 26, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (124, 872585, 525, 26, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (125, 792307, 122423, 26, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (126, 467244, 66728, 26, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (127, 895549, 516, 27, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (128, 466420, 1183917, 27, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (129, 915935, 7152, 27, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (130, 523607, 36662, 27, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (131, 792307, 54693, 27, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (132, 523607, 51329, 28, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (133, 898713, 91671, 28, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (134, 840430, 13242, 28, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (135, 872585, 2037, 28, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (136, 1056360, 2954, 28, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (137, 872585, 5081, 29, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (138, 558915, 1075037, 29, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (139, 346698, 59174, 29, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (140, 895549, 1038, 29, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (141, 840430, 1180099, 29, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (142, 1056360, 1225953, 30, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (143, 466420, 380, 30, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (144, 872585, 3223, 30, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (145, 346698, 30614, 30, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (146, 792307, 103, 30, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (147, 915935, null, 31, 'Justine Triet et Arthur Harari');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (148, 840430, 1224468, 31, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (149, 523607, null, 31, 'Bradley Cooper et Josh Singer');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (150, 839369, null, 31, 'Samy Burch et Alex Mechanik');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (151, 666277, 2518227, 31, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (152, 1056360, 1893777, 32, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (153, 346698, null, 32, 'Greta Gerwig et Noah Baumbach');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (154, 872585, 525, 32, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (155, 792307, 77723, 32, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (156, 467244, 66728, 32, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (157, 346698, null, 33, 'Sarah Greenwood et Katie Spencer');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (158, 466420, null, 33, 'Jack Fisk et Adam Willis');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (159, 753342, null, 33, 'Arthur Max et Elli Griff');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (160, 872585, null, 33, 'Ruth De Jong et Claire Kaufman');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (161, 792307, null, 33, 'James Price, Shona Heath et Zsuzsa Mihalek');

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (162, 346698, 36591, 34, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (163, 466420, 958488, 34, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (164, 753342, null, 34, 'Janty Yates et Dave Crossman');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (165, 872585, 7735, 34, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (166, 792307, 1159587, 34, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (167, 899524, null, 35, 'Karen Hartley Thomas, Suzi Battersby et Ashra Kelly-Blue');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (168, 906126, null, 35, 'Ana López-Puigcerver, David Martí et Montse Ribé');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (169, 523607, null, 35, 'Kazu Hiro, Kay Georgiou et Lori McCoy-Bell');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (170, 872585, 1322015, 35, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (171, 792307, 1159587, 35, 'Nadia Stacey, Mark Coulier et Josh Weston');

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (172, 991708, 6345, 36, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (173, 466420, 275, 36, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (174, 523607, 4867, 36, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (175, 872585, 74401, 36, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (176, 792307, 71086, 36, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (177, 915935, 1616357, 37, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (178, 840430, 32796, 37, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (179, 466420, 3661, 37, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (180, 872585, 1113553, 37, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (181, 792307, 72525, 37, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (182, 670292, null, 38, 'Ian Voigt, Erik Aadahl, Ethan Van der Ryn, Tom Ozanich et Dean Zupancic');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (183, 523607, null, 38, 'Richard King, Steve Morrow, Tom Ozanich, Jason Ruder et Dean Zupancic');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (184, 575264, null, 38, 'Chris Munro, James H. Mather, Chris Burdon et Mark Taylor');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (185, 872585, null, 38, E'Willie Burton, Richard King, Kevin O\'Connell et Gary A. Rizzo');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (186, 467244, null, 38, 'Johnnie Burn et Tarn Willers');

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (187, 670292, null, 39, 'Jay Cooper, Ian Comley, Andrew Roberts et Neil Corbould');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (188, 940721, null, 39, 'Takashi Yamazaki, Kiyoko Shibuya, Masaki Takahashi et Tatsuji Nojima');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (189, 447365, null, 39, 'Stephane Ceretti, Alexis Wajsbrot, Guy Williams et Theo Bialek');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (190, 575264, null, 39, 'Alex Wuttke, Simone Coco, Jeff Sutherland et Neil Corbould');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (191, 753342, null, 39, 'Charley Henley, Luc-Ewen Martin-Fenouillet, Simone Coco et Neil Corbould');

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (192, 626332, null, 40, 'The Fire Inside');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (193, 346698, null, 40, E'I\'m Just Ken');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (194, 1171816, null, 40, 'It Never Went Away');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (195, 466420, null, 40, 'Wahzhazhe');
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (196, 346698, null, 40, 'What Was I Made For?');

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (197, 1056360, 52515, 41, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (198, 335977, 491, 41, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (199, 466420, 72861, 41, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (200, 872585, 928158, 41, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (201, 792307, 4091704, 41, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (202, 937746, null, 42, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (203, 976893, null, 42, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (204, 906126, null, 42, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (205, 998022, null, 42, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (206, 467244, null, 42, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (207, 508883, null, 43, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (208, 976573, null, 43, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (209, 961323, null, 43, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (210, 838240, null, 43, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (211, 569094, null, 43, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (212, 1004683, null, 44, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (213, 1032760, null, 44, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (214, 1069193, null, 44, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (215, 1015356, null, 44, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (216, 1058616, null, 44, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (217, 1169455, null, 45, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (218, 1084765, null, 45, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (219, 971468, null, 45, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (220, 1194636, null, 45, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (221, 923939, null, 45, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (222, 1186227, null, 46, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (223, 1186247, null, 46, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (224, 1203439, null, 46, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (225, 1171861, null, 46, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (226, 1085779, null, 46, null);

INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (227, 950810, null, 47, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (228, 1040371, null, 47, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (229, 1140605, null, 47, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (230, 950822, null, 47, null);
INSERT INTO awd_award_nominee (id, tdmb_movie_id, tdmb_person_id, award_id, name_override) VALUES (231, 1214020, null, 47, null);

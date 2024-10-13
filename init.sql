CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
                                            id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
                                            content text,
                                            metadata json,
                                            embedding vector(1536)
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);

-- 테이블 생성
CREATE TABLE amenities (
                           id SERIAL PRIMARY KEY,
                           title VARCHAR(255),
                           description TEXT,
                           hours VARCHAR(255),
                           location VARCHAR(255)
);

-- 예시 데이터 삽입
INSERT INTO amenities (title, description, hours, location) VALUES
                                                                ('SAY HI',
                                                                 'Your lyf journey begins here! Say hi to our lyf guard and grab a cuppa and some local bites while you check-in with our mobile app.',
                                                                 '24 Hours',
                                                                 'Level 4'),

                                                                ('CONNECT - COWORKING LOUNGE',
                                                                 'Get comfy in the communal lounge: work if you must, but if it’s a break you’re after, there are indulgent couches to chill in and open spaces to work from.',
                                                                 '24 Hours',
                                                                 'Level 4'),

                                                                ('WASH AND HANG - LAUNDERETTE',
                                                                 'Dreary chores are a thing of the past. Load your laundry, then read, chat or chill with a beer while your clothes get cleaned.',
                                                                 '24 Hours',
                                                                 'Level 4'),

                                                                ('BOND - SOCIAL KITCHEN',
                                                                 'Nothing brings people together like good food in the social kitchen – whip up culinary storms or pick up new recipes from like-minded travellers from around the globe.',
                                                                 '24 Hours',
                                                                 'Level 5'),

                                                                ('BURN - SOCIAL GYM',
                                                                 'Work up a sweat in lyf''s life-sized giant hamster wheel that functions as a quirky treadmill, or train up your core with our TRX bands. Gym-ing has never been so fun!',
                                                                 '24 Hours',
                                                                 'Level 6');

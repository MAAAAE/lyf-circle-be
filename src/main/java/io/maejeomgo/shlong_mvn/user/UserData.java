package io.maejeomgo.shlong_mvn.user;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    public static List<User> initUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(
                1L,
                25,
                true,
                "Alice",
                List.of("Cooking", "Book Club"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                2L,
                30,
                false,
                "Bob",
                List.of("Learning Exercise", "Language Exchange"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                3L,
                28,
                true,
                "Charlie",
                List.of("Photography", "Hiking"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                4L,
                22,
                false,
                "Diana",
                List.of("Music Appreciation", "Yoga"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                5L,
                35,
                true,
                "Ethan",
                List.of("Watching Sports", "Cooking"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                6L,
                27,
                false,
                "Fiona",
                List.of("Art Appreciation", "Language Exchange"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                7L,
                31,
                true,
                "George",
                List.of("Learning Exercise", "Board Games"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                8L,
                24,
                false,
                "Hannah",
                List.of("Dance", "Photography"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                9L,
                29,
                true,
                "Ian",
                List.of("Cooking", "Playing Music"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                10L,
                26,
                false,
                "Jenny",
                List.of("Book Club", "Learning Exercise"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                11L,
                33,
                true,
                "Kevin",
                List.of("Photography", "Language Exchange"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                12L,
                21,
                false,
                "Laura",
                List.of("Yoga", "Art Appreciation"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                13L,
                34,
                true,
                "Michael",
                List.of("Board Games", "Hiking"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                14L,
                23,
                false,
                "Nora",
                List.of("Dance", "Learning Exercise"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                15L,
                32,
                true,
                "Oscar",
                List.of("Playing Music", "Cooking"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                16L,
                28,
                false,
                "Paula",
                List.of("Book Club", "Yoga"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                17L,
                27,
                true,
                "Quentin",
                List.of("Hiking", "Language Exchange"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                18L,
                26,
                false,
                "Rachel",
                List.of("Art Appreciation", "Dance"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        users.add(new User(
                19L,
                35,
                true,
                "Sam",
                List.of("Board Games", "Photography"),
                List.of("Morning Person", "Outgoing")
        ));

        users.add(new User(
                20L,
                22,
                false,
                "Tina",
                List.of("Yoga", "Learning Exercise"),
                List.of("Night Owl", "Prefers Others to Initiate Conversation")
        ));

        return users;
    }
}

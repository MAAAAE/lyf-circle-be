package io.maejeomgo.shlong_mvn.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockUserGenerator {

    // 취미 및 성향 데이터
    private static final List<String> hobbies = Arrays.asList(
            "Improving work productivity", "Time management", "Project management", "Freelance networking",
            "Startup founding", "Project collaboration", "Creative idea brainstorming", "Problem solving",
            "Design thinking", "Meditation", "Yoga", "Psychological well-being", "Learning IT skills",
            "Exploring the latest tech trends", "Software development", "Watching movies", "Relaxation",
            "Social activities", "Fashion", "Reusing items", "Environmental protection", "Social conversation",
            "Meeting new people", "Small talk activities", "Beer tasting", "Light conversation", "Leisure time",
            "Playing games", "Fun activities", "Social events", "International cuisine", "Sharing food",
            "Cultural exchange", "Cooking", "Learning new recipes", "Improving cooking skills", "Sharing recipes",
            "Exploring new foods", "Breakfast", "Wine tasting", "Fine dining", "Fitness challenges", "Exercise",
            "Maintaining health", "Stretching", "Jogging", "Outdoor activities", "Dance", "Fitness", "Music",
            "Health management", "Nutrition"
    );

    private static final List<String> characteristics = Arrays.asList(
            "Goal-oriented", "Highly focused", "Cooperative", "Sociable", "Independent", "Innovative",
            "Creative", "Open-minded", "Curious", "Introverted", "Skilled in stress management", "Calm",
            "Knowledge-seeking", "Logical", "Prefers a comfortable environment", "Relaxed", "Environmentally conscious",
            "Friendly", "Casual", "Competitive", "Appreciates diverse cultures", "Passionate about learning",
            "Detail-oriented", "Collaborative", "Enjoys sharing", "Morning person", "Sophisticated", "Active",
            "Enjoys nature", "Energetic", "Fun-loving", "Attentive to self-care", "Focused on self-development"
    );

    // 추가: 언어 데이터
    private static final List<String> languages = Arrays.asList(
            "English", "Korean", "Spanish", "French", "German", "Japanese", "Chinese", "Russian", "Portuguese", "Italian",
            "Arabic", "Hindi", "Dutch", "Swedish", "Turkish", "Greek", "Vietnamese", "Thai", "Hebrew", "Indonesian"
    );

    // 추가: 국가 데이터
    private static final List<String> countries = Arrays.asList(
            "USA", "Korea", "Canada", "Germany", "France", "Brazil", "Japan", "India", "Australia", "Spain",
            "Italy", "China", "Mexico", "Russia", "UK", "Netherlands", "Sweden", "Norway", "South Africa", "New Zealand"
    );

    // 100명의 Mock 사용자를 생성하는 메서드
    public static List<Users> initUsers() {
        List<Users> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            String username = "user_" + (i + 1);
            String password = "password" + (i + 1);
            String nickname = "nickname_" + (i + 1);
            // 나라를 적절히 분배

            // 랜덤으로 2~3개의 취미 선택
            List<String> userHobbies = getRandomItems(hobbies, 2 + random.nextInt(2), random);

            // 랜덤으로 2~3개의 성향 선택
            List<String> userCharacteristics = getRandomItems(characteristics, 2 + random.nextInt(2), random);
            List<String> userLangs = getRandomItems(languages, 2 + random.nextInt(2), random);
            List<String> userCountries = getRandomItems(countries, 2 + random.nextInt(2), random);

            // 사용자 생성 후 리스트에 추가
            users.add(new Users(String.valueOf(i), username, password, nickname, userLangs, userCountries.get(0), userHobbies, userCharacteristics));
        }
        return users;
    }

    // 주어진 리스트에서 랜덤으로 지정된 개수만큼 요소를 선택하는 메서드
    private static List<String> getRandomItems(List<String> source, int count, Random random) {
        List<String> selectedItems = new ArrayList<>();
        while (selectedItems.size() < count) {
            String item = source.get(random.nextInt(source.size()));
            if (!selectedItems.contains(item)) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
}

package io.maejeomgo.shlong_mvn.user;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockUserGenerator {

    // 취미 리스트
    private static final List<String> hobbies = Arrays.asList(
            "Reading",
            "Gaming",
            "Coding",
            "Language Exchange",
            "Cooking",
            "Weight lifting",
            "Yoga",
            "Music",
            "Party"
    );

    // 성향 리스트
    private static final List<String> characteristics = Arrays.asList(
            "Goal-oriented",           // 목표 지향적인 성향
            "Sociable",                // 사람들과 교류하기 좋아하는 성향
            "Collaborative",           // 다른 사람들과 협업을 잘하는 성향
            "Active",                  // 신체적으로 활발한 성향
            "Creative",                // 창의적인 성향
            "Focused on self-development", // 자기 계발에 집중하는 성향
            "Attentive to self-care",   // 자기 관리에 신경 쓰는 성향
            "Relaxed",                 // 여유롭고 스트레스 관리가 잘 되는 성향
            "Detail-oriented",         // 세부 사항에 신경 쓰는 성향
            "Adventurous",             // 새로운 것에 도전하는 성향
            "Competitive",             // 경쟁심이 강한 성향
            "Independent",             // 혼자서도 잘 하는 성향
            "Curious",                 // 새로운 것을 배우고자 하는 성향
            "Environmentally conscious", // 환경에 대한 의식이 있는 성향
            "Friendly",                // 친화력이 좋은 성향
            "Organized",               // 정리 정돈을 잘하는 성향
            "Fun-loving",              // 재미를 추구하는 성향
            "Logical",                 // 논리적이고 이성적인 성향
            "Ambitious"                // 야망이 있는 성향
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
            Faker faker = new Faker();
            String username = faker.name().username();
            System.out.println("Random Username: " + username);
            String password = "password" + (i + 1);
            String nickname = username;

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

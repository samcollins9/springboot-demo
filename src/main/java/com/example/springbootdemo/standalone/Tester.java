package com.example.springbootdemo.standalone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tester {

    public static void main(String[] args) {
        List<VideoGame> games = Arrays.asList(
            new VideoGame("Cyberpunk 2077", "PC", 2020, 7.9),
            new VideoGame("The Witcher 3", "PC", 2015, 9.5),
            new VideoGame("Fortnite", "PC", 2017, 8.0),
            new VideoGame("Super Mario Odyssey", "Nintendo Switch", 2017, 9.7),
            new VideoGame("Halo Infinite", "Xbox", 2021, 8.5),
            new VideoGame("GTA V", "PC", 2015, 9.7)
        );

        List<String> pcGamesTitles = games.stream()
            .filter(game -> game.getPlatform().equals("PC") && game.getReleaseYear() > 2010)
            .sorted((g1, g2) -> Double.compare(g2.getRating(), g1.getRating()))
            .map(VideoGame::getTitle)
            .collect(Collectors.toList());

        System.out.println("Top rated PC games released after 2010:");
        pcGamesTitles.forEach(System.out::println);

        List<String> pcBestGamesTitles = games.stream()
                .filter(game -> game.getRating() > 8.5)
                .sorted((g1, g2) -> Integer.compare(g2.getReleaseYear(), g1.getReleaseYear()))
                .map(game -> "Year "+ game.getReleaseYear() + ", Title " + game.getTitle())
                .collect(Collectors.toList());

        System.out.println("Games rated higher than 8.5 listed by year");
        pcBestGamesTitles.forEach(System.out::println);
    }

}

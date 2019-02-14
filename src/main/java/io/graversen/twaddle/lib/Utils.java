package io.graversen.twaddle.lib;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils
{
    private static final Random random = new Random();

    private static final String[] twaddles = new String[]{
            "My favourite number is: %s",
            "I feel %s today",
            "I have a pet %s",
            "My pet %s is %s!",
            "I need %s %s %s!",
            "I roll the dice: %s",
            "I bet %s on in the %s lottery",
            "We need more %s people in the world...",
            "Could %s be my lucky number? My %s seems to think so",
            "During the day, I felt somewhat %s",
            "Only %s days until I see my %s again!"
    };

    private Utils()
    {
    }

    public static List<String> resourceLines(String resourceName)
    {
        try
        {
            final Path resourcePath = Paths.get(Utils.class.getClassLoader().getResource(resourceName).toURI());
            return Files.readAllLines(resourcePath).stream().map(String::trim).collect(Collectors.toUnmodifiableList());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static String randomTwaddle(List<String> animals, List<String> adjectives)
    {
        final int n = random.nextInt(11);

        switch (n)
        {
            case 0:
                return String.format(twaddles[0], random.nextInt(10000));
            case 1:
                return String.format(twaddles[1], randomOf(adjectives));
            case 2:
                return String.format(twaddles[2], randomOf(animals));
            case 3:
                return String.format(twaddles[3], randomOf(animals), randomOf(adjectives));
            case 4:
                return String.format(twaddles[4], 1 + random.nextInt(10), randomOf(adjectives), randomOf(animals));
            case 5:
                return String.format(twaddles[5], 1 + random.nextInt(6));
            case 6:
                return String.format(twaddles[6], random.nextInt(1000), randomOf(adjectives));
            case 7:
                return String.format(twaddles[7], randomOf(adjectives));
            case 8:
                return String.format(twaddles[8], random.nextInt(1000), randomOf(animals));
            case 9:
                return String.format(twaddles[9], randomOf(adjectives));
            case 10:
                return String.format(twaddles[10], random.nextInt(30), randomOf(animals));
            default:
                return "I don't know what to say";
        }
    }

    public static <T> T randomOf(List<T> list)
    {
        return list.get(random.nextInt(list.size()));
    }

    public static String randomUsername(List<String> animals, List<String> adjectives)
    {
        return String.format("%s_%s", randomOf(adjectives), randomOf(animals));
    }
}

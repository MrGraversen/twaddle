package io.graversen.twaddle.lib;

import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.model.TwaddleModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils
{
    private static final Random random = new Random();
    private static final DateTimeFormatter readableDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Pattern hashTagPattern = Pattern.compile(".*?\\s(#\\w+).*?");

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
            "Only %s days until I see my %s again!",
            "Visiting %s in %d days!",
            "I'm in %s. Any %s guys around?",
            "My favorite color is %s",
            "Why did my %s turn %s?!"
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

    public static Twaddle randomTwaddle(String userId, List<String> animals, List<String> adjectives, List<String> cities, List<String> colors)
    {
        final int n = random.nextInt(15);
        String text = "";

        switch (n)
        {
            case 0:
                text = String.format(twaddles[0], random.nextInt(10000));
                break;
            case 1:
                text = String.format(twaddles[1], randomOf(adjectives));
                break;
            case 2:
                text = String.format(twaddles[2], randomOf(animals));
                break;
            case 3:
                text = String.format(twaddles[3], randomOf(animals), randomOf(adjectives));
                break;
            case 4:
                text = String.format(twaddles[4], 1 + random.nextInt(10), randomOf(adjectives), randomOf(animals));
                break;
            case 5:
                text = String.format(twaddles[5], 1 + random.nextInt(6));
                break;
            case 6:
                text = String.format(twaddles[6], random.nextInt(1000), randomOf(adjectives));
                break;
            case 7:
                text = String.format(twaddles[7], randomOf(adjectives));
                break;
            case 8:
                text = String.format(twaddles[8], random.nextInt(1000), randomOf(animals));
                break;
            case 9:
                text = String.format(twaddles[9], randomOf(adjectives));
                break;
            case 10:
                text = String.format(twaddles[10], random.nextInt(30), randomOf(animals));
                break;
            case 11:
                text = String.format(twaddles[11], randomOf(cities), random.nextInt(30));
                break;
            case 12:
                text = String.format(twaddles[12], randomOf(cities), randomOf(adjectives));
                break;
            case 13:
                text = String.format(twaddles[13], randomOf(colors));
                break;
            case 14:
                text = String.format(twaddles[14], randomOf(animals), randomOf(colors));
                break;
            default:
                text = "I don't know what to say";
                break;
        }

        final Set<String> hashtags = randomHashtags(adjectives);
        text = appendHashtags(text, hashtags);

        return new Twaddle(userId, text, hashtags);
    }

    public static <T> T randomOf(List<T> list)
    {
        return list.get(random.nextInt(list.size()));
    }

    public static String randomUsername(List<String> animals, List<String> adjectives)
    {
        return String.format("%s_%s", randomOf(adjectives), randomOf(animals)).replaceAll("\\s", "").toLowerCase();
    }

    public static String capitalize(String string)
    {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static DateTimeFormatter readableTimeFormatter()
    {
        return readableDateTimeFormatter;
    }

    public static List<String> defaultUsers()
    {
        return List.of("MARTIN", "STEFFEN");
    }

    public static Function<Twaddle, TwaddleModel> mapTwaddle()
    {
        return twaddle -> new TwaddleModel(twaddle.getText(), Utils.readableTimeFormatter().format(twaddle.getCreatedAt()));
    }

    public static List<String> extractHashtags(String text)
    {
        final List<String> hashtags = new ArrayList<>();
        final Matcher matcher = hashTagPattern.matcher(text);

        while (matcher.find())
        {
            hashtags.add(matcher.group(1).replaceAll("#", ""));
        }

        return hashtags;
    }

    public static Set<String> randomHashtags(List<String> adjectives)
    {
        final Set<String> generatedHashtags = new HashSet<>();

        if (random.nextBoolean())
        {
            for (int i = 0; i < 3; i++)
            {
                if (random.nextBoolean())
                {
                    generatedHashtags.add("#".concat(Utils.randomOf(adjectives)));
                }
            }
        }

        return generatedHashtags;
    }

    public static String appendHashtags(String text, Set<String> hashtags)
    {
        return hashtags.isEmpty() ? text : text.concat(" ").concat(String.join(" ", hashtags));
    }
}

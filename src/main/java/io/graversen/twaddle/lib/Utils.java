package io.graversen.twaddle.lib;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils
{
    private static final Random random = new Random();

    private Utils()
    {
    }

    public static List<String> resourceLines(String resourceName)
    {
        try
        {
            final Path resourcePath = Paths.get(Utils.class.getClassLoader().getResource(resourceName).toURI());
            return Collections.unmodifiableList(
                    Files.readAllLines(resourcePath)
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static <T> T randomOf(List<T> list)
    {
        return list.get(random.nextInt(list.size()));
    }
}

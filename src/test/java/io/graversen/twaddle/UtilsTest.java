package io.graversen.twaddle;

import io.graversen.twaddle.lib.Utils;
import org.junit.Test;

import java.util.List;

public class UtilsTest
{
    @Test
    public void testExtractHashtags()
    {
        final List<String> hashtags1 = Utils.extractHashTags("Hello");
        assert hashtags1.isEmpty();

        final List<String> hashtags2 = Utils.extractHashTags("Hello #World");
        assert !hashtags2.isEmpty();
        assert hashtags2.size() == 1;
        assert hashtags2.get(0).equals("World");

        final List<String> hashtags3 = Utils.extractHashTags("Hello #World, it is nice and #sunny today");
        assert !hashtags3.isEmpty();
        assert hashtags3.size() == 2;
        assert hashtags3.get(0).equals("World");
        assert hashtags3.get(1).equals("sunny");
    }
}

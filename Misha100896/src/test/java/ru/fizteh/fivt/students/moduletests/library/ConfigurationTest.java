package ru.fizteh.fivt.students.moduletests.library;

import com.beust.jcommander.JCommander;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class ConfigurationTest extends TestCase {

    Configuration configuration = new Configuration();
    JCommander jCommander = new JCommander(configuration);

    @DataProvider
    public static Object[][] parseArgument() {
        return new Object[][] {
                {
                        "-s -q \"anime\" --hideRetweets",
                        "anime",
                        null,
                        true,
                        true,
                        10,
                        false
                },

                {
                        "-l 15 -p \"Moscow\" -q \"naruto\"",
                        "naruto",
                        "Moscow",
                        false,
                        false,
                        15,
                        false
                }
        };
    }

    @Test
    @UseDataProvider("parseArgument")
    public void testParseArgument(String argumentString, String expectedQuery,
                                  String expectedPlace, boolean expectedStream,
                                  boolean expectedHideRetweets, int expectedLimitTweets,
                                  boolean expectedHelp) {

        jCommander.parse(argumentString.split(" "));
        assertEquals(configuration.getQuery(), expectedQuery);
        assertEquals(configuration.getPlace(), expectedPlace);
        assertEquals(configuration.isStream(), expectedStream);
        assertEquals(configuration.isHideRetweets(), expectedHideRetweets);
        assertEquals(configuration.getLimitTweets(), expectedLimitTweets);
        assertEquals(configuration.isHelp(), expectedHelp);
    }
}

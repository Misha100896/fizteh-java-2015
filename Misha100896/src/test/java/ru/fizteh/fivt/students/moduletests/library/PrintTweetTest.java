package ru.fizteh.fivt.students.moduletests.library;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;

import twitter4j.Status;
import twitter4j.User;

import java.sql.Time;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class PrintTweetTest extends TestCase {

    PrintTweet printTweet = new PrintTweet();

    @Mock
    private Status mockedStatus = mock(Status.class);
    @Mock
    private Status mockedRetweetedStatus = mock(Status.class);
    @Mock
    private User mockedUser = mock(User.class);
    @Mock
    private User mockedRetweetedUser = mock(User.class);

    @Before
    public void setUp() {

        when(mockedStatus.getRetweetedStatus()).
                thenReturn(mockedRetweetedStatus);
        String tweetText = "Hello world!";

        when(mockedStatus.getText()).thenReturn(tweetText);
        when(mockedRetweetedStatus.getText()).thenReturn(tweetText);

        when(mockedStatus.getUser()).thenReturn(mockedUser);
        when(mockedRetweetedStatus.getUser()).thenReturn(mockedRetweetedUser);

        when(mockedUser.getName()).thenReturn("Misha");
        when(mockedRetweetedUser.getName()).thenReturn("Semyon");

        when(mockedStatus.isRetweet()).thenReturn(true);
        when(mockedRetweetedStatus.isRetweet()).thenReturn(false);

        when(mockedStatus.isRetweeted()).thenReturn(false);
        when(mockedRetweetedStatus.isRetweeted()).thenReturn(true);

        when(mockedRetweetedStatus.getRetweetCount()).thenReturn(2);

    }

    @Test
    public void testPrintTweet() {
        String expected = "@Misha: retweeted @Semyon: Hello world!\n------------------------------------------------------\n";
        assertEquals(printTweet.printTweet(mockedStatus), expected);
    }

    @Test
    public void testPrintTime() {

        //Test1
        String expected = "[только что] ";
        Date date;
        date = new Date(System.currentTimeMillis());
        assertEquals(printTweet.printTime(date), expected);

        //Test2
        date.setTime(date.getTime() - 1000 * 60 * 10 - 10);
        expected = "[10 минут назад] ";
        assertEquals(printTweet.printTime(date), expected);
    }
}

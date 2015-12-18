package ru.fizteh.fivt.students.moduletests.library;

import twitter4j.Status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PrintTweet {

    private static final String SEPARATE =
            "------------------------------------------------------";

    public static String printTweet(Status tweet) {
        String result = "@" + tweet.getUser().getName() + ": ";
        if (tweet.isRetweet()) {
            result += "retweeted @" + tweet.getRetweetedStatus().getUser().getName() +
                    ": " + tweet.getText();
        } else {
            result += tweet.getText() + "(" + tweet.getRetweetCount() + "retweets)";
        }

        result += '\n' + SEPARATE + '\n';
        return result;
    }

    public static String printTime(Date date) {

        String result = "[";

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime tweetTime = date.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (ChronoUnit.MINUTES.between(tweetTime, currentTime) < 2) {
            result += "только что";
        } else {
            if (ChronoUnit.HOURS.between(tweetTime, currentTime) < 1) {
                result += ChronoUnit.MINUTES.between(tweetTime, currentTime)
                        + " минут назад";
            } else {
                if (ChronoUnit.DAYS.between(tweetTime, currentTime) < 1) {
                    result += ChronoUnit.HOURS.between(tweetTime, currentTime)
                            + " чаов назад";
                } else {
                    if (ChronoUnit.DAYS.between(tweetTime, currentTime) == 1) {
                        result += "вчера";
                    } else {
                        result += ChronoUnit.DAYS.between(tweetTime, currentTime)
                                + " дней назад";
                    }
                }
            }
        }

        result += "] ";
        return result;
    }
}

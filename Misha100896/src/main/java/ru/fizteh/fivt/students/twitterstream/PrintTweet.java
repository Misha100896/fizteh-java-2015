package ru.fizteh.fivt.students.twitterstream;

import twitter4j.Status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PrintTweet {

    private static final String SEPARATE =
            "------------------------------------------------------";

    public static void printTweet(Status tweet) {

        System.out.print("@" + tweet.getUser().getName() + ": ");
        if (tweet.isRetweet()) {
            System.out.println("retweeted @" + tweet.getRetweetedStatus().getUser().getName() +
                ": " + tweet.getText());
        } else {
            System.out.println(tweet.getText() + "(" + tweet.getRetweetCount() + "retweets)");
        }

        System.out.println(SEPARATE);
    }

    public static void printTime(Date date) {

        System.out.print("[");

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime tweetTime = date.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (ChronoUnit.MINUTES.between(tweetTime, currentTime) < 2) {
            System.out.print("только что");
        } else {
            if (ChronoUnit.HOURS.between(tweetTime, currentTime) < 1) {
                System.out.print(ChronoUnit.MINUTES.between(tweetTime, currentTime)
                        + " минут назад");
            } else {
                if (ChronoUnit.DAYS.between(tweetTime, currentTime) < 1) {
                    System.out.print(ChronoUnit.HOURS.between(tweetTime, currentTime)
                            + " чаов назад");
                } else {
                    if (ChronoUnit.DAYS.between(tweetTime, currentTime) == 1) {
                        System.out.print("вчера");
                    } else {
                        System.out.print(ChronoUnit.DAYS.between(tweetTime, currentTime)
                                + " дней назад");
                    }
                }
            }
        }

        System.out.print("] ");
    }
}

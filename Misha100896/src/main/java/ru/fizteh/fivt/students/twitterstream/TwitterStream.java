package ru.fizteh.fivt.students.twitterstream;

import com.beust.jcommander.JCommander;
import twitter4j.*;

import java.util.List;

public class TwitterStream {

    private static final int TWEET_DELAY = 1000;

    private Configuration configuration;

    public TwitterStream(String[] args) {
        configuration = new Configuration();
        JCommander jCommander = new JCommander(configuration, args);
        if (configuration.isHelp()) {
            jCommander.usage();
        }
    }

    public static void runStream(Configuration configuration) {

        if (!HaveConnection.netIsAvailable("http://api.twitter.com")) {
            System.err.println("No connection");
            System.exit(1);
        }

        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener statusListener = new StatusAdapter() {

            @Override
            public void onStatus(Status tweet) {
                if (!configuration.isHideRetweets() || !tweet.isRetweet()) {

                    PrintTweet.printTweet(tweet);

                    try {
                        Thread.sleep(TWEET_DELAY);
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread error: " + exception.toString());
                        exception.printStackTrace(System.err);
                        System.exit(1);
                    }
                }
            }

            @Override
            public void onException(Exception exception) {
                System.err.println("Stream error: " + exception.toString());
                exception.printStackTrace(System.err);
                System.exit(1);
            }
        };

        twitterStream.addListener(statusListener);
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(configuration.getQuery());
        if (configuration.getPlace() != null) {

            MyGeoLocation findPlace;
            findPlace = new MyGeoLocation((configuration.getPlace()));
            double[][] bounds = {{findPlace.getBounds().southwest.lng,
                    findPlace.getBounds().southwest.lat},
                    {findPlace.getBounds().northeast.lng,
                     findPlace.getBounds().northeast.lat}};
            filterQuery.locations(bounds);
        }
        twitterStream.filter(filterQuery);
    }

    public static void findTweets(Configuration configuration) {

        if (!HaveConnection.netIsAvailable("http://api.twitter.com")) {
            System.err.println("No connection");
            System.exit(1);
        }

        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query();
        query.setQuery(configuration.getQuery());
        query.setCount(configuration.getLimitTweets());

        if (configuration.getPlace() != null) {
            MyGeoLocation googleFindPlace;
            googleFindPlace = new MyGeoLocation(configuration.getPlace());
            GeoLocation geoLocation;
            geoLocation = new GeoLocation(googleFindPlace.getLocation().lat,
                    googleFindPlace.getLocation().lng);
            query.setGeoCode(geoLocation, googleFindPlace.getRadius(), Query.KILOMETERS);

        }

        try {
            List<Status> tweets = twitter.search(query).getTweets();
            for (Status tweet: tweets) {
                PrintTweet.printTime(tweet.getCreatedAt());
                PrintTweet.printTweet(tweet);
            }
        } catch (TwitterException exception) {
            System.err.println("FindTweetsError: " + exception.toString());
            exception.printStackTrace(System.err);
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        TwitterStream twitterStream = new TwitterStream(args);
        if (twitterStream.configuration != null) {
            if (twitterStream.configuration.isStream()) {
                runStream(twitterStream.configuration);
            } else {
                findTweets(twitterStream.configuration);
            }
        }
    }
}

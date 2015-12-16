package ru.fizteh.fivt.students.twitterstream;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

public class Configuration {
    static final int DEFAULT_LIMIT = 10;

    @Parameter(names = { "--query", "-q"}, description = "Find query")
    private String query = null;

    @Parameter(names = {"--place", "-p"}, description = "Search place")
    private String place = null;

    @Parameter(names = {"--stream", "-s"}, description = "Stream")
    private boolean stream = false;

    @Parameter(names = {"--hideRetweets"}, description = "Hide retweets")
    private boolean hideRetweets = false;

    @Parameter(names = {"--limit", "-l"}, description = "Limit tweets")
    private int limitTweets = DEFAULT_LIMIT;

    @Parameter(names = {"--help", "-h"}, description = "Help for you")
    private boolean help = false;

    public String getQuery() {
        return query;
    }

    public String getPlace() {
        return place;
    }

    public boolean isStream() {
        return stream;
    }

    public boolean isHideRetweets() {
        return hideRetweets;
    }

    public int getLimitTweets() {
        return limitTweets;
    }

    public boolean isHelp() {
        return help;
    }

}
package ru.fizteh.fivt.students.twitterstream;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HaveConnection {

    public static boolean netIsAvailable(String checkHost) {
        try {
            final URL url = new URL(checkHost);
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        } catch (IOException exception) {
            return false;
        }
    }
}

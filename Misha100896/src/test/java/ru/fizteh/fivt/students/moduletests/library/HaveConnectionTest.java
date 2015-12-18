package ru.fizteh.fivt.students.moduletests.library;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class HaveConnectionTest extends TestCase {

    HaveConnection haveConnection = new HaveConnection();

    @DataProvider
    public static Object[][] hostConnection() {
        return new Object[][] {
                {
                        "http://yandex.ru",
                        true
                },
                {
                        "http://youtube.com",
                        true
                },
                {
                        "http://misha100896.com",
                        false
                }
        };
    }

    @Test
    @UseDataProvider("hostConnection")
    public void testHostConnection(String host, boolean connection) {

        assertEquals(haveConnection.netIsAvailable(host), connection);
    }
}
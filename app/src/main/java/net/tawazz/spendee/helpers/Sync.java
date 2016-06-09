package net.tawazz.spendee.helpers;

/**
 * Created by tnyak on 6/06/2016.
 */
public class Sync {

    public static String WEB_DOMAIN = "tawazz.net/spendee/api/";
    public static String LOGIN_URL = String.format("http://%s%s", WEB_DOMAIN, "auth");
    public static String DATA_URL = String.format("http://%s%s", WEB_DOMAIN, "data/");

}

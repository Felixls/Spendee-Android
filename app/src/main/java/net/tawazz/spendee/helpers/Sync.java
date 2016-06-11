package net.tawazz.spendee.helpers;

/**
 * Created by tnyak on 6/06/2016.
 */
public class Sync {

    public static String WEB_DOMAIN = "10.0.0.113:8080/spendee/api/";
    public static String LOGIN_URL = String.format("http://%s%s", WEB_DOMAIN, "auth");
    public static String DATA_URL = String.format("http://%s%s", WEB_DOMAIN, "data/");
    public static String ADD_EXP_URL = String.format("http://%s%s", WEB_DOMAIN, "expenses/add");
    public static String ADD_INC_URL = String.format("http://%s%s", WEB_DOMAIN, "incomes/add");
}

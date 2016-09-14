package net.tawazz.spendee.helpers;

/**
 * Created by tnyak on 6/06/2016.
 */
public class Sync {

    public static String WEB_DOMAIN = "10.0.0.84/spendee/api/";
    public static String LOGIN_URL = String.format("http://%s%s", WEB_DOMAIN, "auth");
    public static String DATA_URL = String.format("http://%s%s", WEB_DOMAIN, "data/");
    public static String ADD_EXP_URL = String.format("http://%s%s", WEB_DOMAIN, "expenses/add");
    public static String ADD_INC_URL = String.format("http://%s%s", WEB_DOMAIN, "incomes/add");
    public static String EXPENSES_GRAPH_URL = String.format("http://%s%s", WEB_DOMAIN, "graph/expenses");
    public static String INCOMES_GRAPH_URL = String.format("http://%s%s", WEB_DOMAIN, "graph/incomes");
    public static String TAGS_GRAPH_URL = String.format("http://%s%s", WEB_DOMAIN, "graph/tags");
    public static String YEAR_OVERVIEW_GRAPH_URL = String.format("http://%s%s", WEB_DOMAIN, "graph/line");
}

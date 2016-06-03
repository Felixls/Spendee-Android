package net.tawazz.androidutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

// Use Tawanda's persistent login manager
public class PersistentLoginManager {

    // User name
    public static final String KEY_USERNAME = "Name";
    // Password
    public static final String KEY_PASSWORD = "Password";
    //intent action
    public static final String INTENT_ACTION = "REMEMBER_ME_INTENT";
    // Sharedpref file name
    private static final String PREF_NAME = "LoginManagerPreferences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    final int PRIVATE_MODE = 0;
    private SharedPreferences preferences;
    private Editor editor;

    public PersistentLoginManager(Context context)
    {
        this.preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.preferences.edit();
    }

    /**
     * remembers the user by saving credentials to shared preferences
     */
    public void rememberMe(String username, String password)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing username and password in shared preferences

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored data
     */
    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_USERNAME, preferences.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    /**
     * Clear data when logging out
     * */
    public void ClearPersitentData()
    {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * this method will check user login status
     * @return If false user is not remember
     * Else user is remembered
     * */
    public boolean userExists()
    {
        return preferences.contains(KEY_USERNAME) &&
                preferences.contains(KEY_PASSWORD) &&
                preferences.contains(IS_LOGIN) &&
                preferences.getBoolean(IS_LOGIN,false);
    }
}

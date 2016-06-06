package net.tawazz.spendee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.tawazz.androidutil.PersistentLoginManager;
import net.tawazz.spendee.AppData.AppData;
import net.tawazz.spendee.helpers.Sync;
import net.tawazz.spendee.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    private AppData appData;
    private Class<?> redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        appData = (AppData) getApplication();

        final PersistentLoginManager manager = new PersistentLoginManager(SplashScreen.this);

        // Redirect class
        redirect = LoginActivity.class;
        if (manager.userExists()) {
            JSONObject auth = new JSONObject();
            JSONObject user = new JSONObject();

            try {
                user.put("username", manager.getUserDetails().get(PersistentLoginManager.KEY_USERNAME));
                user.put("password", manager.getUserDetails().get(PersistentLoginManager.KEY_PASSWORD));
                auth.put("auth", user);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Sync.LOGIN_URL, auth, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if (response.getBoolean("auth")) {
                            JSONObject userResponse = response.getJSONObject("user");
                            User user = new User(userResponse.getString("username"), userResponse.getString("firstname"), userResponse.getString("lastname"), userResponse.getString("email"), userResponse.getInt("user_id"));
                            AppData.user = user;
                            redirect = MainActivity.class;
                            Intent intent = new Intent(SplashScreen.this, redirect);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        Intent intent = new Intent(SplashScreen.this, redirect);
                        startActivity(intent);
                        finish();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Intent intent = new Intent(SplashScreen.this, redirect);
                    startActivity(intent);
                    finish();
                }
            });

            AppData.getWebRequestInstance().getRequestQueue().add(request);
        } else {
            Intent intent = new Intent(SplashScreen.this, redirect);
            startActivity(intent);
            finish();
        }
    }
}

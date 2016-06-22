package net.tawazz.spendee;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.tawazz.androidutil.PersistentLoginManager;
import net.tawazz.spendee.AppData.AppData;
import net.tawazz.spendee.helpers.Sync;
import net.tawazz.spendee.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private TextView errorText, register;
    private Button loginBtn;
    private AppData appData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appData = (AppData) getApplication();
        init();
    }

    private void init() {

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById(R.id.errorText);
        register = (TextView) findViewById(R.id.register);
        loginBtn = (Button) findViewById(R.id.button);

        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        passwordEditText.setTypeface(Typeface.SERIF);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://tawazz.net/spendee/register";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setText("Logging in...");
                loginBtn.setEnabled(false);
                JSONObject auth = new JSONObject();
                JSONObject user = new JSONObject();

                try {
                    user.put("username", usernameEditText.getText().toString());
                    user.put("password", passwordEditText.getText().toString());
                    auth.put("auth", user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest request = new JsonObjectRequest(Sync.LOGIN_URL, auth, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getBoolean("auth")) {

                                PersistentLoginManager manager = new PersistentLoginManager(LoginActivity.this);
                                manager.rememberMe(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                                JSONObject userResponse = response.getJSONObject("user");
                                User user = new User(userResponse.getString("username"), userResponse.getString("firstname"), userResponse.getString("lastname"), userResponse.getString("email"), userResponse.getInt("user_id"));
                                AppData.user = user;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            loginBtn.setText("Log In");
                            loginBtn.setEnabled(true);
                            errorText.setText(e.getMessage());
                            errorText.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loginBtn.setText("Log In");
                        loginBtn.setEnabled(true);
                        errorText.setText(error.toString());
                        errorText.setVisibility(View.VISIBLE);
                    }
                });

                AppData.getWebRequestInstance().getRequestQueue().add(request);
            }
        });
    }

}


package net.tawazz.spendee.AppData;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import net.tawazz.androidutil.WebRequest;
import net.tawazz.spendee.models.User;

import java.lang.reflect.Field;

/**
 * Created by tnyak on 29/05/2016.
 */
public class AppData extends Application {

    public static User user;
    private static Context context;

    public static WebRequest getWebRequestInstance() {
        return WebRequest.getInstance(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Iconify.with(new FontAwesomeModule());
        overrideFont(this);
    }

    private void overrideFont(Context context)
    {
        try {

            final Typeface regularTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Regular.ttf");
            final Typeface boldTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Bold.ttf");
            final Typeface italicTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/LobsterTwo-Regular.ttf");
            final Typeface boldItalicTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/LobsterTwo-BoldItalic.ttf");

            final Field regularTypefaceField = Typeface.class.getDeclaredField("SERIF");
            regularTypefaceField.setAccessible(true);
            regularTypefaceField.set(null, regularTypeface);

            final Field boldTypefaceField = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            boldTypefaceField.setAccessible(true);
            boldTypefaceField.set(null, boldTypeface);

            final Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{
                    regularTypeface, boldTypeface, italicTypeface, boldItalicTypeface
            });
        } catch (Exception e) {
            Log.e("typeface", e.getMessage());
        }
    }


}

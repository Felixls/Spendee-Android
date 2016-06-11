package net.tawazz.spendee.helpers;

import net.tawazz.spendee.AppData.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tnyak on 6/06/2016.
 */
public class AppHelper {

    public static ArrayList<String> MONTHS = new ArrayList<>(Arrays.asList(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ));

    public static ArrayList<Tags> getExpTags(){
        JSONArray tagsArray = null;
        try{
            tagsArray = new JSONArray(
                    "[{\"id\":\"1\",\"name\":\"Food & Drink\"}, {\"id\":\"2\",\"name\":\"Car\"}, {\"id\":\"3\",\"name\":\"Bills\"}, {\"id\":\"4\",\"name\":\"Travel\"}, {\"id\":\"5\",\"name\":\"Family & Friends\"}, {\"id\":\"7\",\"name\":\"Entertainment\"}, {\"id\":\"8\",\"name\":\"Shopping\"}, {\"id\":\"9\",\"name\":\"Accomodation\"}, {\"id\":\"10\",\"name\":\"Healthcare\"}, {\"id\":\"11\",\"name\":\"Clothes\"}, {\"id\":\"12\",\"name\":\"Transport\"}, {\"id\":\"13\",\"name\":\"Pets\"}, {\"id\":\"14\",\"name\":\"Groceries\"}, {\"id\":\"15\",\"name\":\"Drinks\"}, {\"id\":\"16\",\"name\":\"Hobbies\"}, {\"id\":\"17\",\"name\":\"Education\"}, {\"id\":\"18\",\"name\":\"Cinema\"}, {\"id\":\"19\",\"name\":\"Love\"}, {\"id\":\"20\",\"name\":\"Rent\"}, {\"id\":\"21\",\"name\":\"Online Spending\"},{\"id\":\"32\",\"name\":\"Phone\"},{\"id\":\"29\",\"name\":\"Movies\"}]");
        }catch (JSONException e){

        }
        ArrayList<String> icons = new ArrayList<>(
                Arrays.asList("{fa-cutlery}","{fa-car}","{fa-credit-card}",
                        "{fa-plane}", "{fa-users}","{fa-television}",
                        "{fa-shopping-cart}","{fa-home}","{fa-ambulance}","{fa-shopping-bag}",
                        "{fa-subway}","{fa-paw}","{fa-shopping-basket}","{fa-glass}",
                        "{fa-futbol-o}","{fa-graduation-cap}","{fa-film}",
                        "{fa-heart}","{fa-home}","{fa-shopping-cart}","{fa-mobile}","{fa-ticket}"
                        )
        );
        ArrayList<Tags> tags = new ArrayList<>();

        for(int i =0; i< icons.size(); i++){
            try {
                JSONObject tag = tagsArray.getJSONObject(i);
                tags.add(new Tags(tag.getString("name"),icons.get(i),tag.getInt("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return tags;
    }

    public static ArrayList<Tags> getIncTags(){
        JSONArray tagsArray = null;
        try{
            tagsArray = new JSONArray(
                    "[{\"id\":\"22\",\"name\":\"Savings\"}, {\"id\":\"23\",\"name\":\"Gifts\"}, {\"id\":\"24\",\"name\":\"Other\"}, {\"id\":\"25\",\"name\":\"Salary\"}, {\"id\":\"26\",\"name\":\"Loan\"}, {\"id\":\"27\",\"name\":\"Extra Income\"}, {\"id\":\"28\",\"name\":\"Business\"}, {\"id\":\"30\",\"name\":\"Fees\"}, {\"id\":\"31\",\"name\":\"Interest Charges\"}]");
        }catch (JSONException e){

        }
        ArrayList<String> icons = new ArrayList<>(
                Arrays.asList("{fa-briefcase}","{fa-gift}","{fa-question-circle}","{fa-money}",
                        "{fa-credit-card-alt}","{fa-money}","{fa-suitcase}",
                        "{fa-cc-amex}","{fa-hand-pointer-o}"
                )
        );
        ArrayList<Tags> tags = new ArrayList<>();

        for(int i =0; i< icons.size(); i++){
            try {
                JSONObject tag = tagsArray.getJSONObject(i);
                tags.add(new Tags(tag.getString("name"),icons.get(i),tag.getInt("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return tags;
    }
}

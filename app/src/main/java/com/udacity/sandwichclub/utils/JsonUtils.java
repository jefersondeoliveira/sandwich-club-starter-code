package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json){

        try{
            //get values
            JSONObject sandwichJsonObject = new JSONObject(json);
            JSONObject name =  sandwichJsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichJsonObject.getString("placeOfOrigin");
            String description = sandwichJsonObject.getString("description");
            String image = sandwichJsonObject.getString("image");
            JSONArray ingredients = sandwichJsonObject.getJSONArray("ingredients");

            Sandwich sandwich = new Sandwich();

            //set object
            sandwich.setMainName(mainName);
            List<String> alsoKnownList = getListFromJSONArray(alsoKnownAs);
            sandwich.setAlsoKnownAs(alsoKnownList);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            List<String> ingredientsList = getListFromJSONArray(ingredients);
            sandwich.setIngredients(ingredientsList);

            return sandwich;

        }catch (JSONException e){
            return null;
        }

    }

    private static List<String> getListFromJSONArray(JSONArray array){
        List<String> newList = new ArrayList<>();

        try{
            for (int i = 0; i < array.length(); i++){
                newList.add(array.getString(i));
            }
        }catch (JSONException ignored){}

        return newList;
    }
}

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView origin = findViewById(R.id.origin_tv);
        TextView alsoKnown = findViewById(R.id.also_known_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        LinearLayout labelOrigin = findViewById(R.id.label_origin_ll);
        LinearLayout labelAlsoKnown = findViewById(R.id.alsoknown_origin_ll);
        LinearLayout labelIngredients = findViewById(R.id.ingredients_origin_ll);

        description.setText(sandwich.getDescription());

        if(!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())){
            origin.setText(sandwich.getPlaceOfOrigin());
        }else{
            labelOrigin.setVisibility(View.GONE);
        }

        if(!sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnown.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }else {
            labelAlsoKnown.setVisibility(View.GONE);
        }

        if(!sandwich.getIngredients().isEmpty()){
            ingredients.setText(TextUtils.join(", ", sandwich.getIngredients()));
        }else {
            labelIngredients.setVisibility(View.GONE);
        }

    }
}

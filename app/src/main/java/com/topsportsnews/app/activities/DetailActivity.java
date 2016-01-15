package com.topsportsnews.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.topsportsnews.activities.R;
import com.topsportsnews.app.models.SportsItem;


public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleText;
    TextView imageCaptionText;
    TextView bylineText;
    TextView publishedDateText;
    TextView descriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView= (ImageView) findViewById(R.id.image);
        titleText = (TextView)findViewById(R.id.titleTextView);
        imageCaptionText = (TextView) findViewById(R.id.imageCaptionText);
        bylineText = (TextView) findViewById(R.id.bylineText);
        publishedDateText = (TextView)findViewById(R.id.publishedDateText);
        descriptionText = (TextView)findViewById(R.id.descriptionText);


        SportsItem sportsItem = (SportsItem) getIntent().getSerializableExtra("sportsItem");
        String imageUrl = sportsItem.getThumbnail();
        String title = sportsItem.getTitle();
        String imageCaption = sportsItem.getImageCaption();
        String byline = sportsItem.getByline();
        String publishedDate = sportsItem.getPublishedDate();
        String description = sportsItem.getDescription();

        Picasso.with(DetailActivity.this).load(imageUrl).into(imageView);
        titleText.setText(title);
        bylineText.setText(byline);
        imageCaptionText.setText(imageCaption);
        publishedDateText.setText("Published on " + publishedDate);
        descriptionText.setText("Stroy - "+description);

    }
}

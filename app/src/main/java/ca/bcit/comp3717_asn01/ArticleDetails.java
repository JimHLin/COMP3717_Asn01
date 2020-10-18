package ca.bcit.comp3717_asn01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        String url = getIntent().getStringExtra("url");
        String urlToImage = getIntent().getStringExtra("urlToImage");
        String publishedAt = getIntent().getStringExtra("publishedAt");
        String content = getIntent().getStringExtra("content");

        ImageView photo = findViewById(R.id.photo);
        if (urlToImage != null) {
            new ImageDownloaderTask(photo).execute(urlToImage);
        }

        TextView tvTitle = findViewById(R.id.articleTitle);
        tvTitle.setText(String.format("Title: %s", title));

        TextView tvAuthor = findViewById(R.id.author);
        tvAuthor.setText(String.format("Author: %s", author));

        TextView tvDescription = findViewById(R.id.description);
        tvDescription.setText(String.format("Description: %s", description));

        TextView tvUrl = findViewById(R.id.url);
        tvUrl.setText(String.format("Url: %s", url));

        TextView tvPublishedAt = findViewById(R.id.publishedAt);
        tvPublishedAt.setText(String.format("PublishedAt: %s", publishedAt));

        TextView tvContent = findViewById(R.id.content);
        tvContent.setText(String.format("Content: %s", content));
    }
}
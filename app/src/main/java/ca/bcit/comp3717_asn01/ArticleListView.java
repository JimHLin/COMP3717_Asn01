package ca.bcit.comp3717_asn01;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;

public class ArticleListView extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    // URL to get contacts JSON
    private static String serviceURL;
    private ArrayList<Article> articleList;
    private String query;
    private String from;
    private String apiKey = "a0a590f6f1284c5b95db7eeb60d1bc29";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.query = getIntent().getStringExtra("prompt");
        this.from = LocalDate.now().minusWeeks(1).toString();
        setContentView(R.layout.activity_article_list_view);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        articleList = new ArrayList<Article>();
        lv = findViewById(R.id.articleList);
        int[] colors = {0, 0xFF4682B4, 0}; // red for the example
        lv.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        lv.setDividerHeight(1);
        serviceURL = "https://newsapi.org/v2/everything" +
                "?q=" + query +
                "&from=" + from +
                "&apiKey=" + apiKey;

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ArticleListView.this, ArticleDetails.class);
                intent.putExtra("title", articleList.get(i).getTitle());
                intent.putExtra("author", articleList.get(i).getAuthor());
                intent.putExtra("description", articleList.get(i).getDescription());
                intent.putExtra("url", articleList.get(i).getUrl());
                intent.putExtra("urlToImage", articleList.get(i).getUrlToImage());
                intent.putExtra("publishedAt", articleList.get(i).getPublishedAt());
                intent.putExtra("content", articleList.get(i).getContent());
                intent.putExtra("sourceName", articleList.get(i).getSource().getName());
                startActivity(intent);
            }
        });

        new GetContacts().execute();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return false;
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(serviceURL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                Log.d(TAG, "Json: " + jsonStr);

                Gson gson = new Gson();
                BaseArticle baseArticle = gson.fromJson(jsonStr, BaseArticle.class);
                articleList = baseArticle.getArticles();
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArticleAdapter adapter = new ArticleAdapter(ArticleListView.this, articleList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }
    }
}
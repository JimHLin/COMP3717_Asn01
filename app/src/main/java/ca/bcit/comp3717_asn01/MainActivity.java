package ca.bcit.comp3717_asn01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.sendButton);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText prompt = findViewById(R.id.firstPageQuery);
        String promptString = prompt.getText().toString();
        Intent i = new Intent(this, ArticleListView.class);
        i.putExtra("prompt", promptString);
        startActivity(i);
    }
}
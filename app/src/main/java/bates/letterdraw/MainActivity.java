package bates.letterdraw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void draw(View v) {
        EditText t = (EditText)findViewById(R.id.draw_text);
        Intent i = new Intent(this, DrawActivity.class);
        i.putExtra("text", t.getText().toString());
        startActivity(i);
    }

}

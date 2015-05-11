package bates.letterdraw;

import android.app.Activity;
import android.os.Bundle;

public class DrawActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        LetterDrawView v = (LetterDrawView)findViewById(R.id.draw_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            v.setText(extras.getString("text"));
        }
    }

}

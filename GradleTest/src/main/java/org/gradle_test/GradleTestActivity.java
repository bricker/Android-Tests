package org.gradle_test;

import org.joda.time.LocalTime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GradleTestActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradle_test_layout);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalTime currentTime = new LocalTime();
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("The current local time is: " + currentTime);
    }

}

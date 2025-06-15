package com.example.electricitybillcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialize the GitHub link TextView
        TextView githubLink = findViewById(R.id.githubLink);

        // Create underlined text for the GitHub link
        SpannableString underlinedText = new SpannableString("GitHub Profile");
        underlinedText.setSpan(new UnderlineSpan(), 0, underlinedText.length(), 0);
        githubLink.setText(underlinedText);

        // Set click listener for the GitHub link
        githubLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String githubUrl = "https://github.com/DanishSuhaimi";

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                startActivity(browserIntent);
            }
        });

        TextView studentName = findViewById(R.id.studentName);
        TextView studentId = findViewById(R.id.studentId);
        TextView courseCode = findViewById(R.id.courseCode);
        TextView courseName = findViewById(R.id.courseName);

        studentName.setText("Wan Muhammad Danish Fakhri Bin Suhaimi");
        studentId.setText("2023197357");
        courseCode.setText("ICT602");
        courseName.setText("Mobile Technology and Development");
    }
}
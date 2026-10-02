package cs.linda.iscg7424assessment2exercise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cs.linda.iscg7424assessment2exercise.exercise1.Exercise1Activity;
import cs.linda.iscg7424assessment2exercise.exercise2.Exercise2Activity;

public class MainActivity extends AppCompatActivity {
    Button exercise1, exercise2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        exercise1 = findViewById(R.id.btn_exercise1);
        exercise2 = findViewById(R.id.btn_exercise2);
        exercise1.setOnClickListener(v -> {
            Intent intent = new Intent(this, Exercise1Activity.class);
            startActivity(intent);
        });
        exercise2.setOnClickListener(v -> {
            Intent intent = new Intent(this, Exercise2Activity.class);
            startActivity(intent);
        });
    }
}
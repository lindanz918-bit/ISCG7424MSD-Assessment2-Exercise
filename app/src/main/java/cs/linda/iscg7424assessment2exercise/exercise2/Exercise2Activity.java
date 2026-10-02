package cs.linda.iscg7424assessment2exercise.exercise2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cs.linda.iscg7424assessment2exercise.R;

public class Exercise2Activity extends AppCompatActivity {
    UserController api;
    List<User> userList = new ArrayList<>();
    RecyclerView recyclerView;
    UserAdapter adapter;

    Button btnCallAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_exercise2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);
        btnCallAPI = findViewById(R.id.btn_call);
        btnCallAPI.setOnClickListener(this::getData);
    }

    public void getData(View view) {
        api = new UserController();
        api.start(new UserController.ApiCallback() {
            @Override
            public void onSuccess(Users usersData) {
                if (usersData != null && usersData.getData() != null) {
                    userList.clear();
                    userList.addAll(usersData.getData());
                    adapter.notifyDataSetChanged(); // 刷新 RecyclerView 卡片列表
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(Exercise2Activity.this, "Failed: " + errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showData(View view) {
        if (api != null && api.users != null && api.users.getData() != null) {
            Log.d("UserData", api.users.getData().toString());
            Toast.makeText(this, api.users.getData().toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data hasn't been loaded yet or is empty. Please click Get Data first.", Toast.LENGTH_SHORT).show();
        }
    }
}
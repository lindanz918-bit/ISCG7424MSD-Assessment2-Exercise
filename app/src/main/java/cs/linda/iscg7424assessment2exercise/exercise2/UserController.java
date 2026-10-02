package cs.linda.iscg7424assessment2exercise.exercise2;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserController implements Callback<Users> {
    final String BASE_URL = "https://reqres.in/api/";
    public Users users;
    private ApiCallback apiCallback;

    public interface ApiCallback {
        void onSuccess(Users usersData);
        void onFailure(String errorMsg);
    }
    public void start(ApiCallback callback) {
        this.apiCallback = callback;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserRESTAPI userRESTAPI = retrofit.create(UserRESTAPI.class);
        Call<Users> call = userRESTAPI.getUsers();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Users> call, Response<Users> response) {
        if (response.isSuccessful()) {
            users = response.body();
            Log.d("USER_API", "Getting users data successfully " + users.getData().size());
            if (users.data != null) {
                Log.d("USER_API", users.data.toString());
                if(apiCallback != null) {
                    apiCallback.onSuccess(users);
                }
            } else {
                Log.d("USER_API", "Error get users data");

            }

        }
    }

    @Override
    public void onFailure(Call<Users> call, Throwable t) {
        Log.d("USER_API", "Error getting users");
        if(apiCallback != null) {
            apiCallback.onFailure(t.getMessage());
        }
    }
}

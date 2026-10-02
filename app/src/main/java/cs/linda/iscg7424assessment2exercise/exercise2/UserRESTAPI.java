package cs.linda.iscg7424assessment2exercise.exercise2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface UserRESTAPI {
    @Headers("x-api-key: free_user_3D1ujpQvBj2GbzwnPw54bxtPvwv")
    @GET("users?page=2")
    Call<Users> getUsers();
}
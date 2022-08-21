package com.example.cakeclient;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UserService {
    @GET("/users")
    public void getUsers(Callback<List<UserApi>> callback);
    @GET("/users/{id}")
    public void getUsersById(@Path("id") Integer id,Callback<UserApi> callback);
    @POST("/users")
    public void addUsers(@Body UserApi userApi,Callback<UserApi> callback );

    @GET("/recipes")
    public void getRecipes(Callback<List<CatalogApi>> callback);
    @POST("/recipes")
    public void addRecipes(@Body RecipeApi recipeApi,Callback<RecipeApi> callback );

    @GET("/orders")
    public void getOrders(Callback<List<OrderApi>> callback);
    @POST("/orders")
    public void addOrders(@Body OrderApi orderApi,Callback<OrderApi> callback );

    @GET("/crusts")
    public void getCrusts(Callback<List<IngredientsApi>> callback);
    @GET("/creams")
    public void getCreams(Callback<List<IngredientsApi>> callback);
    @GET("/fillings")
    public void getFillings(Callback<List<IngredientsApi>> callback);
    @GET("/additions")
    public void getAdditions(Callback<List<IngredientsApi>> callback);
}

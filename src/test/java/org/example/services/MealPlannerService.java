package org.example.services;

import org.example.requests.ShoppingItemRequest;
import org.example.responses.DeleteItemResponse;
import org.example.responses.ShoppingItemResponse;
import org.example.responses.ShoppingListResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface MealPlannerService {
    @GET("mealplanner/{username}/shopping-list")
    Call<ShoppingListResponse> getShoppingList(
            @Path("username") String username,
            @Query("apiKey") String apiKey,
            @Query("hash") String userHash
    );

    @POST("mealplanner/{username}/shopping-list/items")
    Call<ShoppingItemResponse> addItemToShoppingList(
            @Path("username") String username,
            @Body ShoppingItemRequest item,
            @Query("apiKey") String apiKey,
            @Query("hash") String userHash
    );

    @DELETE("mealplanner/{username}/shopping-list/items/{id}")
    Call<DeleteItemResponse> deleteItemFromShoppingList(
            @Path("username") String username,
            @Path("id") int id,
            @Query("apiKey") String apiKey,
            @Query("hash") String userHash
    );
}

package org.example;

import java.util.stream.Stream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import org.example.requests.ShoppingItemRequest;
import org.example.responses.ShoppingItemResponse;
import org.example.responses.ShoppingListResponse;
import org.example.services.MealPlannerService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppTest {
    static final String BASE_URL = "https://api.spoonacular.com/";
    static final String API_KEY = "8795fdd66bae4e79b7f8496b2a2fc4d2";
    static final String API_USER = "reogina";
    static final String API_HASH = "a6a342ea2460f0b0079c2d0c6106cb84e238092a";

    static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    static Retrofit CreateRetrofit() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build())
                .build();
    }

    @DisplayName("Add item to shopping list")
    @ParameterizedTest
    @MethodSource("addItemSource")
    @Order(1)
    void addItemToShoppingList(ShoppingItemRequest item) {
        var retrofit = CreateRetrofit();
        var service = retrofit.create(MealPlannerService.class);
        var svcCall = service.addItemToShoppingList(API_USER, item, API_KEY, API_HASH);
        try {
            var response = svcCall.execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Stream<ShoppingItemRequest> addItemSource() {
        return Stream.of(
                new ShoppingItemRequest("1 package baking powder"),
                new ShoppingItemRequest("1 package baking powder", true),
                new ShoppingItemRequest("pasta", true)
        );
    }

    static ShoppingListResponse shoppingListResponse;

    @DisplayName("Get shopping list")
    @Test
    @Order(2)
    void getShoppingList() {
        var retrofit = CreateRetrofit();
        var service = retrofit.create(MealPlannerService.class);
        var svcCall = service.getShoppingList(API_USER, API_KEY, API_HASH);
        try {
            var response = svcCall.execute();
            shoppingListResponse = response.body();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            assertThat(shoppingListResponse.getAisles().size(), equalTo(3));
            assertThat(shoppingListResponse.getAisles().stream()
                    .filter(a -> a.getName().equals("Baking")).count(), equalTo(1L));
            assertThat(shoppingListResponse.getAisles().stream()
                    .filter(a -> a.getName().equals("Pasta and Rice")).count(), equalTo(1L));
            assertThat(shoppingListResponse.getAisles().stream()
                    .filter(a -> a.getName().equals("Non-Food Items")).count(), equalTo(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Delete item from shopping list")
    @ParameterizedTest
    @MethodSource("deleteItemSource")
    @Order(3)
    void deleteItemFromShoppingList(ShoppingItemResponse item) {
        var retrofit = CreateRetrofit();
        var service = retrofit.create(MealPlannerService.class);
        var svcCall = service.deleteItemFromShoppingList(API_USER, item.getId(), API_KEY, API_HASH);
        try {
            var response = svcCall.execute();
            var deletion = response.body();
            assert deletion != null;
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            assertThat(deletion.getStatus(), CoreMatchers.is("success"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Stream<ShoppingItemResponse> deleteItemSource() {
        return shoppingListResponse
                .getAisles().stream()
                .flatMap(a -> a.getItems().stream());
    }
}
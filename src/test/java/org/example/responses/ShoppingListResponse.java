package org.example.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingListResponse {
    private final List<ShoppingAisleResponse> aisles = new ArrayList<>();

    @JsonGetter("aisles")
    public List<ShoppingAisleResponse> getAisles() {
        return aisles;
    }
}

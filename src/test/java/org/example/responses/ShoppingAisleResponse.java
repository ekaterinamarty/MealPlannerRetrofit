package org.example.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingAisleResponse {
    @JsonProperty("aisle")
    private String name;

    private final List<ShoppingItemResponse> items = new ArrayList<>();

    @JsonGetter("items")
    public List<ShoppingItemResponse> getItems() {
        return items;
    }
}

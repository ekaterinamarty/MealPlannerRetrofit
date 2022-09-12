package org.example.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingItemRequest {
    @JsonProperty("item")
    private String item;

    @JsonProperty("aisle")
    private String aisle;

    @JsonProperty("parse")
    private boolean parse;

    public ShoppingItemRequest(String a) {
        this.item = a;
    }

    public ShoppingItemRequest(String a, boolean c) {
        this.item = a;
        this.parse = c;
    }
}
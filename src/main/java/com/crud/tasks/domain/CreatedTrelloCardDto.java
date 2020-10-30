package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedTrelloCardDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
//
//    @JsonProperty("badges")
//    private Badges badges;

    @JsonProperty("shortUrl")
    private String shortUrl;
}
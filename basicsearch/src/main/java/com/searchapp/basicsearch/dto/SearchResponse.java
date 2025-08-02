package com.searchapp.basicsearch.dto;

import com.searchapp.basicsearch.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponse {
    private long count;
    private List<User> results;
}

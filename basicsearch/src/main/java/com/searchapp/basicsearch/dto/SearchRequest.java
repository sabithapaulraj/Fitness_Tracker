package com.searchapp.basicsearch.dto;

import lombok.Data;

@Data
public class SearchRequest {
    private String interest;
    private int minSalary;
}

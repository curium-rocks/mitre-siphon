package xyz.andrewkboyd.mitresiphon.dto;

import lombok.Data;


import java.util.List;

public @Data
class SearchResult {
    private long totalMatches;
    private long offset;
    private long count;
    private List<Object> matches;
}

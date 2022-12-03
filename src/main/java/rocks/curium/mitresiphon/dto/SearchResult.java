package rocks.curium.mitresiphon.dto;

import lombok.Data;


import java.math.BigInteger;
import java.util.List;

public @Data
class SearchResult {
    private BigInteger totalMatches;
    private BigInteger offset;
    private long count;
    private List<Object> matches;
}

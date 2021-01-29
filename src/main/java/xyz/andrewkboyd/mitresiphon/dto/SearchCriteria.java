package xyz.andrewkboyd.mitresiphon.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import java.math.BigInteger;
import java.util.List;

public @Data
class SearchCriteria {
    private BigInteger offset;
    @Max(100)
    private long count;
    private List<String> terms;
}

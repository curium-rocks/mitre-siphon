package rocks.curium.mitresiphon.dto;

import java.math.BigInteger;
import java.util.List;
import lombok.Data;

public @Data class SearchResult {
  private BigInteger totalMatches;
  private BigInteger offset;
  private long count;
  private List<Object> matches;
}

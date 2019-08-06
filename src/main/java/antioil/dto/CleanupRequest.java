package antioil.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CleanupRequest {

    public final List<Integer> areaSize;
    public final List<Integer> startingPosition;
    public final List<List<Integer>> oilPatches;
    public final String navigationInstructions;

    @JsonCreator
    public CleanupRequest(@JsonProperty(value = "areaSize", required = true) List<Integer> areaSize,
                          @JsonProperty(value = "startingPosition", required = true) List<Integer> startingPosition,
                          @JsonProperty(value = "oilPatches", required = true) List<List<Integer>> oilPatches,
                          @JsonProperty(value = "navigationInstructions", required = true) String navigationInstructions) {

        this.areaSize = areaSize;
        this.startingPosition = startingPosition;
        this.oilPatches = oilPatches;
        this.navigationInstructions = navigationInstructions;
    }
}

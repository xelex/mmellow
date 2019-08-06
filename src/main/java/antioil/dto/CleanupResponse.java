package antioil.dto;

import java.util.List;

public class CleanupResponse {
    public final List<Integer> finalPosition;
    public final Integer oilPatchesCleaned;

    public CleanupResponse(List<Integer> finalPosition, Integer oilPatchesCleaned) {
        this.finalPosition = finalPosition;
        this.oilPatchesCleaned = oilPatchesCleaned;
    }
}

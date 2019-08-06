package antioil.api;

import antioil.dto.CleanupRequest;
import antioil.dto.CleanupResponse;
import antioil.service.CleanupTracker;
import antioil.service.Point;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static antioil.service.Point.point;
import static java.util.stream.Collectors.toSet;

@RestController
public class CleanerController {

    @PostMapping("/cleanup")
    public CleanupResponse cleanup(@RequestBody CleanupRequest request) {
        final Point size = point(request.areaSize);
        final Point start = point(request.startingPosition);
        final Set<Point> oil = request.oilPatches.stream()
                .map(Point::point)
                .collect(toSet());

        final CleanupTracker tracker = new CleanupTracker(size, oil);

        int oilBefore = tracker.oilLeft();
        final Point end = tracker.cleanPath(start, request.navigationInstructions);
        int oilAfter = tracker.oilLeft();

        return new CleanupResponse(end.asList(), oilBefore - oilAfter);
    }
}

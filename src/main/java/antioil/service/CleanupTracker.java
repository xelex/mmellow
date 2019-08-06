package antioil.service;

import java.util.HashSet;
import java.util.Set;

public class CleanupTracker {

    private Point size;
    private final Set<Point> oil;

    public CleanupTracker(Point size, Set<Point> oil) {
        this.size = size;
        this.oil = new HashSet<>(oil);

        if (size.x < 1 || size.y < 1) throw new NavigationException("Navigation area is too small");

        for (Point o : oil) {
            if (isInvalid(o)) throw new NavigationException("Oil is out of bound");
        }
    }

    public Point cleanPath(Point start, String path) {
        if (isInvalid(start)) throw new NavigationException("Starting point is invalid");

        oil.remove(start);

        Point current = start;
        for (char c : path.toUpperCase().toCharArray()) {
            switch (c) {
                case 'N':
                    current = current.navigate(0, 1);
                    break;
                case 'S':
                    current = current.navigate(0, -1);
                    break;
                case 'W':
                    current = current.navigate(-1, 0);
                    break;
                case 'E':
                    current = current.navigate(1, 0);
                    break;
                default:
                    throw new NavigationException("Illegal navigation command: " + c);
            }

            if (isInvalid(current)) {
                throw new NavigationException("Navigation command is out of range");
            }

            oil.remove(current);
        }

        return current;
    }

    public int oilLeft() {
        return oil.size();
    }

    private boolean isInvalid(Point point) {
        return point.x < 0 || point.y < 0 || point.x >= size.x || point.y >= size.y;
    }
}

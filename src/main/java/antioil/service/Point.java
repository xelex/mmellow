package antioil.service;

import java.util.List;
import java.util.Objects;

public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point navigate(int dx, int dy) {
        return point(x + dx, y + dy);
    }

    public List<Integer> asList() {
        return List.of(x, y);
    }

    public static Point point(int x, int y) {
        return new Point(x, y);
    }

    public static Point point(List<Integer> dimension) {
        if (dimension == null || dimension.size() != 2) {
            throw new IllegalArgumentException("Incorrect dimensional array size");
        }

        return new Point(dimension.get(0), dimension.get(1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

package antioil.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static antioil.service.Point.point;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CleanupTrackerTest {

    @Test
    void shouldInitializeSizeShouldNotBeToSmall() {
        // expect
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(0, 0), Set.of()));
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(1, 0), Set.of()));
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(0, 1), Set.of()));
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(-1, -1), Set.of()));
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(1, -1), Set.of()));
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(-1, 1), Set.of()));
    }

    @Test
    void oilIsOutOfRange() {
        // expect
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(1, 1), Set.of(point(-1, -1))));
        assertThrows(NavigationException.class, () -> new CleanupTracker(point(1, 1), Set.of(point(1, 1))));
    }

    @Test
    void startingPointShouldBeCorrect() {
        // when
        CleanupTracker sut = new CleanupTracker(point(1, 1), Set.of(point(0, 0)));

        assertThrows(NavigationException.class, () -> sut.cleanPath(point(1, 1), ""));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(-1, -1), ""));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 1), ""));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(1, 0), ""));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, -1), ""));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(-1, 0), ""));
    }

    @Test
    void emptyOilsAreSupported() {
        // when
        CleanupTracker sut = new CleanupTracker(point(1, 1), Set.of());

        // then
        assertEquals(0, sut.oilLeft());
    }

    @Test
    void startingPointIsCleared() {
        // when
        CleanupTracker sut = new CleanupTracker(point(1, 1), Set.of(point(0, 0)));

        int before = sut.oilLeft();
        Point ended = sut.cleanPath(point(0, 0), "");
        int after = sut.oilLeft();

        // then
        assertEquals(1, before);
        assertEquals(0, after);
        assertEquals(point(0, 0), ended);
    }

    @Test
    void exceptionOnIncorrectFlowCharacters() {
        // when
        CleanupTracker sut = new CleanupTracker(point(2, 2), Set.of(point(0, 0), point(1,1)));

        // then
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "L"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "R"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "U"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "D"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), " "));
    }

    @Test
    void exceptionWhenPathIsOutOfBound() {
        // when
        CleanupTracker sut = new CleanupTracker(point(2, 2), Set.of(point(0, 0), point(1,1)));

        // then
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "S"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "W"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "EE"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "NN"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "EWW"));
        assertThrows(NavigationException.class, () -> sut.cleanPath(point(0, 0), "NSS"));
    }

    @Test
    void oilIsClearedOnPath() {
        // when
        CleanupTracker sut = new CleanupTracker(point(2, 2), Set.of(point(0, 0), point(1,1)));

        int before = sut.oilLeft();
        Point ended = sut.cleanPath(point(0, 0), "NESW");
        int after = sut.oilLeft();

        // then
        assertEquals(2, before);
        assertEquals(0, after);
        assertEquals(point(0, 0), ended);
    }
}
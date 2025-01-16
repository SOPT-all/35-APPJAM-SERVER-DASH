package be.dash.dashserver.core.domain.lesson;

import java.util.List;

public record Locations(List<Location> items) {
    public static Locations of(List<Location> items) {
        return new Locations(items);
    }
}

package be.dash.dashserver.core.domain.common;

import java.util.Objects;

public class Keyword {

    public static final String ANY = "";

    private final String value;

    public Keyword(String value) {
        this.value = value;
    }

    public Keyword() {
        this.value = ANY;
    }

    public String getValue() {
        if (value == null || value.isEmpty()) {
            return ANY;
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(value, keyword.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

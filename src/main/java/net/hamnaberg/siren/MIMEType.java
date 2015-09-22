package net.hamnaberg.siren;

import static net.hamnaberg.siren.util.StreamUtils.*;

import java.util.*;

public final class MIMEType {
    private final String major;
    private final String minor;
    private final SortedMap<String, String> parameters;

    public static Optional<MIMEType> parse(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    MIMEType(String major, String minor) {
        this(major, minor, new TreeMap<>(Comparator.naturalOrder()));
    }

    MIMEType(String major, String minor, SortedMap<String, String> parameters) {
        this.major = major;
        this.minor = minor;
        this.parameters = Collections.unmodifiableSortedMap(parameters);
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public SortedMap<String, String> getParameters() {
        return parameters;
    }

    public boolean equals(MIMEType mt, boolean includeParams) {
        boolean equals = mt.getMajor().equals(getMajor()) && mt.getMinor().equals(getMajor());
        if (includeParams) {
            equals = zip(getParameters().entrySet().stream(), mt.getParameters().entrySet().stream(), (a, b) -> new AbstractMap.SimpleImmutableEntry<>(a, b)).
                    allMatch((e) -> e.getKey().equals(e.getValue()));
        }
        return equals;
    }

    public boolean includes(MIMEType mt) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MIMEType mimeType = (MIMEType) o;

        return equals(mimeType, true);
    }

    @Override
    public int hashCode() {
        int result = major.hashCode();
        result = 31 * result + minor.hashCode();
        result = 31 * result + parameters.hashCode();
        return result;
    }
}

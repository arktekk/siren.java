package net.hamnaberg.siren;

import static net.hamnaberg.siren.util.StreamUtils.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MIMEType {
    public static final MIMEType All = new MIMEType("*", "*");
    public static final MIMEType JSON = new MIMEType("application", "json");
    public static final MIMEType URLEncoded = new MIMEType("application", "x-www-form-urlencoded");
    private static final Pattern MIMETypePattern = Pattern.compile("(\\w+|\\*)/(\\w+|\\*);?(.*)?");


    private final String major;
    private final String minor;
    private final SortedMap<String, String> parameters;

    public static Optional<MIMEType> parse(String s) {
        Matcher matcher = MIMETypePattern.matcher(s);
        if (matcher.matches()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            TreeMap<String, String> params = new TreeMap<>(Comparator.naturalOrder());
            if (matcher.groupCount() == 4) {
                String group = matcher.group(3);
                String[] parts = group.split(";");
                for (String part : parts) {
                    String[] split = part.split("=");
                    if (split.length == 2) {
                        params.put(split[0].trim(), split[1].trim());
                    } else {
                        params.put(split[0].trim(), "");
                    }
                }
            }
            return Optional.of(new MIMEType(major, minor, params));
        }
        return Optional.empty();
    }

    public static MIMEType application(String minor) {
        return new MIMEType("application", minor);
    }

    public static MIMEType application(String minor, Map<String, String> map) {
        return new MIMEType("application", minor, parameterMap(map));
    }

    public static MIMEType text(String minor) {
        return new MIMEType("text", minor);
    }

    public static MIMEType text(String minor, Map<String, String> map) {
        return new MIMEType("text", minor, parameterMap(map));
    }

    public static SortedMap<String, String> parameterMap(Map<String, String> m) {
        if (m instanceof SortedMap) return ((SortedMap<String, String>)m);
        TreeMap<String, String> tm = new TreeMap<>(Comparator.naturalOrder());
        tm.putAll(m);
        return tm;
    }

    public MIMEType(String major, String minor) {
        this(major.toLowerCase(), minor.toLowerCase(), new TreeMap<>(Comparator.naturalOrder()));
    }

    public MIMEType(String major, String minor, SortedMap<String, String> parameters) {
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

    public String format() {
        String formatted = String.format("%s/%s", this.major, this.minor);
        if (!parameters.isEmpty()) {
            String params = parameters.entrySet().stream().
                    map(e -> String.format("%s=%s", e.getKey(), e.getValue())).
                    collect(Collectors.joining("; ", "; ", ""));
            return formatted + params;
        }
        return formatted;
    }

    public boolean includes(MIMEType mt) {
        boolean includes = (mt == null || All.equals(mt, false) || this.equals(mt));
        if (!includes) {
            includes = this.major.equals(mt.major) && (this.minor.equals("*")|| this.minor.equals(mt.minor));
        }
        return includes;
    }

    public boolean equals(MIMEType mt, boolean includeParams) {
        boolean equals = mt.getMajor().equals(getMajor()) && mt.getMinor().equals(getMajor());
        if (includeParams) {
            equals = zip(getParameters().entrySet().stream(), mt.getParameters().entrySet().stream(), (a, b) -> new AbstractMap.SimpleImmutableEntry<>(a, b)).
                    allMatch((e) -> e.getKey().equals(e.getValue()));
        }
        return equals;
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

    @Override
    public String toString() {
        return "MIMEType{" +
                "major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}

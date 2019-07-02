package www.limo.com.mymovies.entities;

import java.util.*;
import java.io.IOException;

public enum OriginalLanguage {
    EN, RU;

    public String toValue() {
        switch (this) {
        case EN: return "en";
        case RU: return "ru";
        }
        return null;
    }

    public static OriginalLanguage forValue(String value) throws IOException {
        if (value.equals("en")) return EN;
        if (value.equals("ru")) return RU;
        throw new IOException("Cannot deserialize OriginalLanguage");
    }
}

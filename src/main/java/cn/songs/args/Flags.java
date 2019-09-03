package cn.songs.args;

import java.util.HashMap;
import java.util.Map;

class Flags<T> {
    private Map<String, T> record = new HashMap<>();
    void addFlag(String flag, T defaultValue) {
        record.put(flag, defaultValue);
    }

    T getFlag(String flag) {
        if (!record.containsKey(flag)) {
            throw new IllegalArgumentException("getting an undefined flag: " + flag);
        }
        return record.get(flag);
    }

    void setFlag(String flag, T value) {
        if (!record.containsKey(flag)) {
            throw new IllegalArgumentException("setting an undefined flag: " + flag);
        }
        record.put(flag, value);
    }
}

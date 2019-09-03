package cn.songs.args;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Args {
    private Map<String, Type> flagTypes = new HashMap<>();
    private Flags<Boolean> boolFlags = new Flags<>();
    private Flags<Integer> intFlags = new Flags<>();
    private Flags<String> stringFlags = new Flags<>();
    private Flags<String[]> stringsFlags = new Flags<>();

    public void parse(String[] args) {
        Iterator<String> argIter = Arrays.asList(args).iterator();
        boolean stay = false;
        String arg = "";
        while (argIter.hasNext() || stay) {
            arg = stay ? arg : argIter.next().trim();
            stay = false;
            if (arg.matches("^-\\w+$")) {
                String flag = arg.substring(1);
                if (!flagTypes.containsKey(flag)) {
                    throw new IllegalArgumentException("undefined flag: " + flag);
                } else if (flagTypes.get(flag).equals(Boolean.TYPE)) {
                    boolFlags.setFlag(flag, true);
                } else if (flagTypes.get(flag).equals(Integer.TYPE)) {
                    String param = argIter.next();
                    intFlags.setFlag(flag, Integer.valueOf(param));
                } else if (flagTypes.get(flag).equals(String.class)) {
                    String param = argIter.next();
                    stringFlags.setFlag(flag, param);
                } else if (flagTypes.get(flag).equals(String[].class)) {
                    List<String> params = new ArrayList<>();
                    while (argIter.hasNext()) {
                        arg = argIter.next().trim();
                        if (arg.matches("^-\\w+$")) {
                            stay = true;
                            break;
                        }
                        params.add(arg);
                    }
                    String[] param = new String[params.size()];
                    param = params.toArray(param);
                    stringsFlags.setFlag(flag, param);
                } else {
                    throw new IllegalArgumentException("undefined flag: " + flag);
                }
            } else {
                throw new IllegalArgumentException("arg without flag: " + arg);
            }
        }
    }

    public void setBool(String flag, boolean defaultValue) {
        flagTypes.put(flag, Boolean.TYPE);
        boolFlags.addFlag(flag, defaultValue);
    }

    public boolean getBool(String flag) {
        try {
            return boolFlags.getFlag(flag);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("flag type mismatch: " + flag);
        }
    }

    public void setInt(String flag, int defaultValue) {
        flagTypes.put(flag, Integer.TYPE);
        intFlags.addFlag(flag, defaultValue);
    }

    public int getInt(String flag) {
        try {
            return intFlags.getFlag(flag);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("flag type mismatch: " + flag);
        }
    }

    public void setString(String flag, String defaultValue) {
        flagTypes.put(flag, String.class);
        stringFlags.addFlag(flag, defaultValue);
    }

    public String getString(String flag) {
        try {
            return stringFlags.getFlag(flag);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("flag type mismatch: " + flag);
        }
    }

    public void setStrings(String flag, String[] defaultValue) {
        flagTypes.put(flag, String[].class);
        stringsFlags.addFlag(flag, defaultValue);
    }

    public String[] getStrings(String flag) {
        try {
            return stringsFlags.getFlag(flag);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("flag type mismatch: " + flag);
        }
    }
}

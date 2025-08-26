package xyz.kayaaa.xenon.shared.tools.string;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringSplitter {

    public String[] splitByNewline(String string) {
        return string.split("\\R");
    }

}

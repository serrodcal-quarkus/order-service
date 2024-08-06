package dev.serrodcal.resources.util;

import java.util.Optional;

public class CheckParamUtil {

    public static Optional<Integer> checkPage(Optional<Integer> page) {
        if (page.isPresent() && page.get() < 0)
            return Optional.of(0);

        return page;
    }

    public static Optional<Integer> checkSize(Optional<Integer> size) {
        if (size.isPresent() && size.get() < 10)
            return Optional.of(10);

        return size;
    }

}

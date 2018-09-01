package com.software.lukaszwelnicki.msc.database;

import java.util.Arrays;

public enum FillProcessStatus {
    DISPOSED(true),
    NOT_DISPOSED(false);

    boolean isDisposed;

    FillProcessStatus(boolean isDisposed) {
        this.isDisposed = isDisposed;
    }

    public static FillProcessStatus getByStatus(boolean status) {
        return Arrays.stream(FillProcessStatus.values())
                .filter(f -> f.isDisposed == status)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}

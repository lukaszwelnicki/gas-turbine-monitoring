package com.software.lukaszwelnicki.msc.service;

import com.software.lukaszwelnicki.msc.database.DatabaseFiller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseFillProcessService {

    private final DatabaseFiller databaseFiller;

    private Disposable databaseFillProcess;

    public Disposable startDatabaseFillProcess() {
        if (databaseFillProcess == null || databaseFillProcess.isDisposed()) {
            databaseFillProcess = databaseFiller.fillDatabase().subscribe(m -> log.info("Database fill process - inserted document id: " + m.getId()));
        }
        return databaseFillProcess;
    }

    public void killDatabaseFillProcess() {
        Optional.ofNullable(databaseFillProcess)
                .ifPresent(Disposable::dispose);
    }

    public Mono<FillProcessStatus> isProcessDisposed() {
        return Optional.ofNullable(databaseFillProcess)
                .map(Disposable::isDisposed)
                .map(FillProcessStatus::getByStatus)
                .map(Mono::just)
                .orElseGet(() -> Mono.just(FillProcessStatus.DISPOSED));
    }

    public enum FillProcessStatus {
        DISPOSED(true),
        NOT_DISPOSED(false);

        boolean isDisposed;

        FillProcessStatus(boolean isDisposed) {
            this.isDisposed = isDisposed;
        }

        static FillProcessStatus getByStatus(boolean status) {
            return Arrays.stream(FillProcessStatus.values())
                    .filter(f -> f.isDisposed == status)
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }
    }
}

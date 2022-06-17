package com.wf2311.wakatime.sync;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class WakatimeSyncApplication implements QuarkusApplication {
    public static void main(String[] args) {
        Quarkus.run(WakatimeSyncApplication.class, args);
    }

    @Override
    public int run(String... args) {
        Quarkus.waitForExit();
        return 0;
    }
}


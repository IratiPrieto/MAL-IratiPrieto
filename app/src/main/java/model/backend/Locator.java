package model.backend;

public final class Locator {

    private static final Backend backend = new FBBackend();

    public static Backend getBackend() {
        return backend;
    }
}

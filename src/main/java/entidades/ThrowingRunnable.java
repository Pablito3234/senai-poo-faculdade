package entidades;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}

package example.jonathan.tipping;

public class test {
    private static final test ourInstance = new test();

    public static test getInstance() {
        return ourInstance;
    }

    private test() {
    }
}

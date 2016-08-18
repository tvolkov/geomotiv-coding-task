package rubiconproject.writer;

public class ConsoleOutput implements Output {
    @Override
    public void printOutput(String content) {
        System.out.println(content);
    }
}

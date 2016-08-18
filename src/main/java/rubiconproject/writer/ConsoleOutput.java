package rubiconproject.writer;

public class ConsoleOutput implements Output {
    @Override
    public void output(String content) {
        System.out.println(content);
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static java.nio.charset.Charset.defaultCharset;

public class CraftingInterpreters {

    private static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        run(new String(bytes, defaultCharset()));
    }

    private static void runPrompt() throws IOException {
        var isr = new InputStreamReader(System.in);
        var bufferedReader = new BufferedReader(isr);
        for(;;) {
            System.out.print("> ");
            var line = bufferedReader.readLine();
            if (Objects.equals(line, "exit")) {
                break;
            }
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        var scanner = new TokenScanner(source);
        var scannedTokens = scanner.scanTokens();
        if (hadError) {
            System.exit(65);
        }
    }
}

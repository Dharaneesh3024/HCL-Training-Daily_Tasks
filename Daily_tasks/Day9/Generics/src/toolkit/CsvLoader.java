package toolkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class CsvLoader {

    private CsvLoader() {}

    public static <T> List<T> load(Path file, Function<String[], T> mapper) throws IOException {
        List<T> rows = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                rows.add(mapper.apply(line.split(",")));
            }
        }
        return rows;
    }
}

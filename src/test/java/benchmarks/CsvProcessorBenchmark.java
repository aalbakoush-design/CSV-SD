package benchmarks;

import com.example.csv.CsvProcessor;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class CsvProcessorBenchmark {

    private CsvProcessor processor;
    private String largeCsvData;

    @Setup
    public void setup() {
        processor = new CsvProcessor();
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Name,Value,Category\n");
        for (int i = 0; i < 10000; i++) {
            sb.append(i).append(",Name").append(i).append(",").append(i * 10).append(",")
                    .append(i % 2 == 0 ? "A" : "B").append("\n");
        }
        largeCsvData = sb.toString();
    }

    @Benchmark
    public void testProcessData() throws IOException {
        StringReader reader = new StringReader(largeCsvData);
        StringWriter writer = new StringWriter();
        processor.processData(reader, writer, "Category", "A");
    }
}

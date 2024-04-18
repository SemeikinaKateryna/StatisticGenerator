package reading;

import java.util.List;

public interface IJsonReader<T> {
    List<T> read(int threadCount);
}

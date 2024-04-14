package writing;

import java.util.Map;

public interface IXmlWriter {
    boolean write(Map<Object,Integer> statisticsMap, String attribute);
}

package statistics;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "statistics")
public class Statistics {
    @XmlElement(name = "item")
    private List<Item> items;
    public void setItems(List<Item> items) {
        this.items = items;
    }
    @XmlType(name = "item", propOrder = {
            "value", "count"
    })

    public static class Item {
        @XmlElement(name = "value")
        private String value;
        @XmlElement(name = "count")
        private int count;

        public void setValue(String value) {
            this.value = value;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

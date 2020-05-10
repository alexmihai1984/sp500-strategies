package rocks.alexmihai.sp500strategies.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class Data {

    private final ObjectMapper objectMapper;

    private List<Sp500Value> values;

    @Autowired
    public Data(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() throws IOException {
        this.values = this.objectMapper.readValue(
                new ClassPathResource("data_json.json").getFile(),
                new TypeReference<List<Sp500Value>>() {}
         );
    }

    public List<Sp500Value> getValues() {
        return this.values;
    }
}

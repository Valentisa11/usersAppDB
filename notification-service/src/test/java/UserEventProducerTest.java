import com.example.NotificationServiceApplication;
import com.example.model.UserEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;



import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest(classes = NotificationServiceApplication.class)
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = {"user-events"})
public class UserEventProducerTest {

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @MockBean
    private KafkaTemplate<String, UserEvent> mockKafkaTemplate;

    @Test
    public void testSendUserEvent() {
        UserEvent event = new UserEvent("test@example.com", "CREATE");


        kafkaTemplate.send("user-events", event);


        verify(mockKafkaTemplate, times(1)).send("user-events", event);
    }
}
import com.example.NotificationServiceApplication;
import com.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;



import static org.mockito.Mockito.*;


@SpringBootTest(classes = NotificationServiceApplication.class)
@ActiveProfiles("test")
public class EmailServiceTest {

    @MockBean
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        // Arrange
        String email = "test@example.com";
        String message = "Test message";

        // Act
        emailService.sendEmail(email, message);

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
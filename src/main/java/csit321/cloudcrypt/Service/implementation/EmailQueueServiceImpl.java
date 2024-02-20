package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.EmailQueue;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.EmailQueueRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.EmailQueueService;

import org.apache.catalina.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailQueueServiceImpl implements EmailQueueService {

    public final UserAccountRepository userAccountRepository;

    public final EmailQueueRepository emailQueueRepository;

    public final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailQueueServiceImpl(UserAccountRepository userAccountRepository,
                                 EmailQueueRepository emailQueueRepository,
                                 JavaMailSender javaMailSender) {
        this.userAccountRepository = userAccountRepository;
        this.emailQueueRepository = emailQueueRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String createEmailQueue(UserAccount userAccount, String subject, String body) {
        // Create a new email queue
        EmailQueue emailQueue = new EmailQueue();
        emailQueue.setUserAccount(userAccount);
        emailQueue.setId(java.util.UUID.randomUUID());
        emailQueue.setEmailSubject(subject);
        emailQueue.setEmailBody(body);
        emailQueue.setStatus("PENDING");
        emailQueueRepository.save(emailQueue);
        return "Success";
    }

    @Transactional
    @Override
    public String readEmailQueue(String param) {
        // Read the email queue
        List<EmailQueue> emailQueueList = switch (param) {
            case "ALL" -> emailQueueRepository.findAll();
            case "PENDING" -> emailQueueRepository.findEmailQueueByStatus("PENDING");
            case "SENT" -> emailQueueRepository.findEmailQueueByStatus("SENT");
            default -> {
                EmailQueue emailQueue = emailQueueRepository.findEmailQueueByUserAccount_Username(param).orElse(null);
                yield List.of(emailQueue);
            }
        };

        ArrayNode an = objectMapper.createArrayNode();
        for (EmailQueue emailQueue : emailQueueList) {
            ObjectNode on = objectMapper.createObjectNode();
            on.put("id", emailQueue.getId().toString());
            on.put("userAccount", emailQueue.getUserAccount().getUsername());
            on.put("emailSubject", emailQueue.getEmailSubject());
            on.put("emailBody", emailQueue.getEmailBody());
            on.put("status", emailQueue.getStatus());
            on.put("timeSent", emailQueue.getTimeSent().toString());
            an.add(on);
        }
        return an.toString();
    }

    @Override
    public String updateEmailQueue(UUID uuid, Map<String, String> updates) {
        // Update the email queue
        EmailQueue emailQueue = emailQueueRepository.findById(uuid).orElse(null);
        if (emailQueue == null) {
            return "EmailQueue not found";
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "emailSubject" -> emailQueue.setEmailSubject(value);
                case "emailBody" -> emailQueue.setEmailBody(value);
                case "status" -> emailQueue.setStatus(value);
                default -> {}
            }
        });
        emailQueueRepository.save(emailQueue);
        return "EmailQueue updated";
    }

    @Override
    public String deleteEmailQueue(Map<String, String> param) {
        param.forEach((key, value) -> {
            switch (key) {
                case "id" -> emailQueueRepository.deleteById(UUID.fromString(value));
                case "userAccount" -> emailQueueRepository.deleteEmailQueueByUserAccount_Username(value);
                default -> {}
            }
        });
        System.out.println("EmailQueue deleted");
        return "EmailQueue deleted";
    }

    @Override
    public String scheduleEmailQueue() {
        // Schedule the email queue
        List<EmailQueue> emailQueueList = emailQueueRepository.findEmailQueueByStatus("PENDING");
        for (EmailQueue emailQueue : emailQueueList) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailQueue.getUserAccount().getEmail());
            mailMessage.setSubject(emailQueue.getEmailSubject());
            mailMessage.setText(emailQueue.getEmailBody());
            javaMailSender.send(mailMessage);
            emailQueue.setStatus("SENT");
            emailQueueRepository.save(emailQueue);
        }
        return "Success";
    }
}

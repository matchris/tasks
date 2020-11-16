package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    public String grammarForm;

    public static final String SUBJECT = "Tasks: Tasks quantity";

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildDailyQuantityTasksReport(mail.getMessage()), true);
        };
    }

    @Scheduled(cron = "0 0 8 * * *")
    //@Scheduled(fixedDelay = 10000)
    public void sendEmailQuantityTasks() {
        long size = taskRepository.count();
        grammarForm = (size >= 2) ? "tasks" : "task";
        Mail mail = new Mail(adminConfig.getAdminMail(),
                SUBJECT,
                "Currently in database you got: " + size + " " + grammarForm + ".", null);
        MimeMessagePreparator mimeMessage = createMimeMessage(mail);
        javaMailSender.send(mimeMessage);

    }

}
package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    private final String CRUD_URL = "http://localhost:8888/tasks_frontend";
    private List<String> functionality = new ArrayList<>();

    public String buildTrelloCardEmail(String message) {

        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", CRUD_URL);
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_details", adminConfig.getCompanyDetails());
        context.setVariable("goodbye_message", "See you soon!");
        context.setVariable("preview_message", "Welcome");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality",functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyQuantityTasksReport(String message) {

            Context context = new Context();
            context.setVariable("message",message);
            context.setVariable("tasks_url", CRUD_URL);
            context.setVariable("button", "show your tasks");
            context.setVariable("app_name", adminConfig.getAppName());
            context.setVariable("app_version", adminConfig.getAppVersion());
            context.setVariable("admin_config", adminConfig);
            context.setVariable("goodbye_message","See you soon!");
            return templateEngine.process("mail/quantity-tasks-report",context);
    }
}

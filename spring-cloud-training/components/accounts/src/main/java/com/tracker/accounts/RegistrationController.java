package com.tracker.accounts;

import com.tracker.users.UserInfo;
import com.tracker.users.data.UserRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public UserInfo create(@RequestBody RegistrationForm form) {
        UserRecord record = service.createUserWithAccount(form.name);
        return new UserInfo(record.id, record.name, "registration info");
    }
}

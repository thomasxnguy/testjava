package com.tracker.accounts;

import com.tracker.accounts.data.AccountDataGateway;
import com.tracker.accounts.data.AccountDataGateway;
import com.tracker.users.data.UserDataGateway;
import com.tracker.users.data.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserDataGateway userDataGateway;
    private final AccountDataGateway accountDataGateway;

    public RegistrationService(UserDataGateway userDataGateway, AccountDataGateway accountDataGateway) {
        this.userDataGateway = userDataGateway;
        this.accountDataGateway = accountDataGateway;
    }

    public UserRecord createUserWithAccount(String name) {
        UserRecord user = userDataGateway.create(name);
        accountDataGateway.create(user.id, String.format("%s's account", name));
        return user;
    }
}

package com.tracker.accounts;

import com.tracker.accounts.AccountController;
import com.tracker.accounts.AccountInfo;
import com.tracker.accounts.data.AccountDataGateway;
import com.tracker.accounts.data.AccountRecord;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.tracker.accounts.AccountInfo.accountInfoBuilder;
import static com.tracker.accounts.data.AccountRecord.accountRecordBuilder;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    private AccountDataGateway gateway = mock(AccountDataGateway.class);
    private AccountController controller = new AccountController(gateway);

    @Test
    public void testList() {
        AccountRecord recordToFind = accountRecordBuilder()
            .id(13L)
            .ownerId(2L)
            .name("Some Name")
            .build();
        doReturn(singletonList(recordToFind)).when(gateway).findAllByOwnerId(anyLong());


        List<AccountInfo> result = controller.list(13);


        verify(gateway).findAllByOwnerId(13L);
        Assertions.assertThat(result).containsExactly(accountInfoBuilder()
            .id(13L)
            .ownerId(2L)
            .name("Some Name")
            .info("account info")
            .build()
        );
    }
}

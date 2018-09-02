package com.tracker.users;

import com.tracker.users.UserController;
import com.tracker.users.UserInfo;
import com.tracker.users.data.UserDataGateway;
import com.tracker.users.data.UserRecord;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserDataGateway gateway = mock(UserDataGateway.class);
    private UserController controller = new UserController(gateway);

    @Test
    public void testShow() {
        doReturn(new UserRecord(3L, "Some User")).when(gateway).find(anyLong());

        UserInfo result = controller.show(3);

        verify(gateway).find(3L);
        assertThat(result).isEqualTo(new UserInfo(3L, "Some User", "user info"));
    }
}

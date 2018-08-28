package com.tracker.timesheets;

import io.pivotal.pal.tracker.timesheets.*;
import io.pivotal.pal.tracker.timesheets.data.TimeEntryDataGateway;
import io.pivotal.pal.tracker.timesheets.data.TimeEntryFields;
import io.pivotal.pal.tracker.timesheets.data.TimeEntryRecord;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class TimeEntryControllerTest {

    private TimeEntryDataGateway gateway = mock(TimeEntryDataGateway.class);
    private ProjectClient client = mock(ProjectClient.class);
    private TimeEntryController controller = new TimeEntryController(gateway, client);


    @Test
    public void testCreate() {
        TimeEntryRecord record = TestBuilders.testTimeEntryRecordBuilder().projectId(12).build();
        TimeEntryFields fields = TestBuilders.testTimeEntryFieldsBuilder().projectId(12).build();
        TimeEntryForm form = TestBuilders.testTimeEntryFormBuilder().projectId(12).build();

        doReturn(record).when(gateway).create(fields);
        doReturn(new ProjectInfo(true)).when(client).getProject(anyLong());


        ResponseEntity<TimeEntryInfo> result = controller.create(form);


        verify(client).getProject(12L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(TestBuilders.testTimeEntryInfoBuilder().projectId(12).build());
    }

    @Test
    public void testCreate_WhenFailed() {
        doReturn(new ProjectInfo(false)).when(client).getProject(anyLong());


        ResponseEntity<TimeEntryInfo> result = controller.create(TestBuilders.testTimeEntryFormBuilder().projectId(12).build());


        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void testList() {
        List<TimeEntryRecord> records = asList(
            TestBuilders.testTimeEntryRecordBuilder().id(10).build(),
            TestBuilders.testTimeEntryRecordBuilder().id(11).build(),
            TestBuilders.testTimeEntryRecordBuilder().id(12).build()
        );
        doReturn(records).when(gateway).findAllByUserId(anyLong());
        int userId = 210;


        List<TimeEntryInfo> result = controller.list(userId);


        verify(gateway).findAllByUserId(userId);

        Assertions.assertThat(result).containsExactlyInAnyOrder(
            TestBuilders.testTimeEntryInfoBuilder().id(10).build(),
            TestBuilders.testTimeEntryInfoBuilder().id(11).build(),
            TestBuilders.testTimeEntryInfoBuilder().id(12).build()
        );
    }
}

package com.tracker.timesheets;

import com.tracker.timesheets.TimeEntryForm;
import com.tracker.timesheets.TimeEntryInfo;
import com.tracker.timesheets.data.TimeEntryFields;
import com.tracker.timesheets.data.TimeEntryRecord;

import java.time.LocalDate;

import static com.tracker.timesheets.TimeEntryForm.timeEntryFormBuilder;
import static com.tracker.timesheets.TimeEntryInfo.timeEntryInfoBuilder;
import static com.tracker.timesheets.data.TimeEntryFields.timeEntryFieldsBuilder;
import static com.tracker.timesheets.data.TimeEntryRecord.timeEntryRecordBuilder;

public class TestBuilders {

    public static TimeEntryRecord.Builder testTimeEntryRecordBuilder() {
        return timeEntryRecordBuilder()
            .id(11)
            .projectId(12)
            .userId(13)
            .date(LocalDate.parse("2017-09-19"))
            .hours(20);
    }

    public static TimeEntryFields.Builder testTimeEntryFieldsBuilder() {
        return timeEntryFieldsBuilder()
            .projectId(12)
            .userId(13)
            .date(LocalDate.parse("2017-09-19"))
            .hours(20);
    }

    public static TimeEntryForm.Builder testTimeEntryFormBuilder() {
        return timeEntryFormBuilder()
            .projectId(12)
            .userId(13)
            .date("2017-09-19")
            .hours(20);
    }

    public static TimeEntryInfo.Builder testTimeEntryInfoBuilder() {
        return timeEntryInfoBuilder()
            .id(11)
            .projectId(12)
            .userId(13)
            .date("2017-09-19")
            .hours(20)
            .info("time entry info");
    }
}

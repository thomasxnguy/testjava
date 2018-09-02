package com.tracker.projects;

import com.tracker.projects.ProjectForm;
import com.tracker.projects.ProjectInfo;
import com.tracker.projects.data.ProjectFields;
import com.tracker.projects.data.ProjectRecord;

import static com.tracker.projects.ProjectForm.projectFormBuilder;
import static com.tracker.projects.ProjectInfo.projectInfoBuilder;
import static com.tracker.projects.data.ProjectFields.projectFieldsBuilder;
import static com.tracker.projects.data.ProjectRecord.projectRecordBuilder;

public class TestBuilders {

    public static ProjectRecord.Builder testProjectRecordBuilder() {
        return projectRecordBuilder()
            .id(9L)
            .accountId(23L)
            .name("MyInfo")
            .active(true);
    }

    public static ProjectInfo.Builder testProjectInfoBuilder() {
        return projectInfoBuilder()
            .id(9L)
            .accountId(23L)
            .name("MyInfo")
            .active(true)
            .info("project info");
    }

    public static ProjectFields.Builder testProjectFieldsBuilder() {
        return projectFieldsBuilder()
            .accountId(23L)
            .name("MyInfo")
            .active(true);
    }

    public static ProjectForm.Builder testProjectFormBuilder() {
        return projectFormBuilder()
            .accountId(23L)
            .name("MyInfo")
            .active(true);
    }
}

package com.tracker.projects;

import com.tracker.instrumentation.latency.InstrumentLatency;
import com.tracker.projects.data.ProjectDataGateway;
import com.tracker.projects.data.ProjectFields;
import com.tracker.projects.data.ProjectRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tracker.projects.ProjectInfo.projectInfoBuilder;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectDataGateway gateway;

    public ProjectController(ProjectDataGateway gateway) {
        this.gateway = gateway;
    }

    @PostMapping
    public ResponseEntity<ProjectInfo> create(@RequestBody ProjectForm form) {
        ProjectRecord record = gateway.create(formToFields(form));
        return new ResponseEntity<>(present(record), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProjectInfo> list(@RequestParam long accountId) {
        return gateway.findAllByAccountId(accountId)
            .stream()
            .map(this::present)
            .collect(toList());
    }

    @InstrumentLatency
    @GetMapping("/{projectId}")
    public ProjectInfo get(@PathVariable long projectId) {
        log.info("returning project info for project id {}", projectId);
        ProjectRecord record = gateway.find(projectId);
        if (record != null) {
            return present(record);
        }

        return null;
    }


    private ProjectFields formToFields(ProjectForm form) {
        return ProjectFields.projectFieldsBuilder()
            .accountId(form.accountId)
            .name(form.name)
            .active(form.active)
            .build();
    }

    private ProjectInfo present(ProjectRecord record) {
        return projectInfoBuilder()
            .id(record.id)
            .accountId(record.accountId)
            .name(record.name)
            .active(record.active)
            .info("project info")
            .build();
    }
}

package com.codesoom.assignment.resources;

import com.codesoom.assignment.HttpStatusCode;
import com.codesoom.assignment.models.Response;
import com.codesoom.assignment.models.Task;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GetTaskResource extends TaskResource
        implements ResourceHandler {

    @Override
    public Response handleRequest(List<Task> tasks, String path, String body) throws IOException {

        if (path.equals(TASKS)) {
            return new Response(tasksToJSON(tasks), HttpStatusCode.OK);
        }
        if (path.startsWith(TASKS_ID)) {
            String param = path.substring(TASKS_ID.length());
            Long id = parseParam(param);
            Optional<Task> taskOpt = getTaskById(tasks, id);
            Task task = taskOpt.orElseThrow(() -> new NoSuchElementException("Not found"));
            return new Response(taskToJSON(task), HttpStatusCode.OK);
        }
        return new Response(HttpStatusCode.BAD_REQUEST.getStatus(), HttpStatusCode.BAD_REQUEST);
    }
}

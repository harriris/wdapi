package com.github.harriris.wdapi;

import com.github.harriris.wdapi.restapi.models.JsonResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WdapiErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<JsonResponse>handleError(HttpServletRequest request) {
        final HttpStatus status;
        final Object rawStatus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (rawStatus == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            status = HttpStatus.valueOf(Integer.parseInt(rawStatus.toString()));
        }

        String message = null;
        final Object rawMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (rawMessage != null) {
            message = rawMessage.toString();
        }
        if (message == null || message.isEmpty()) {
            if (status.is5xxServerError()) {
                message = "Internal Server Error";
            } else if (status.equals(HttpStatus.NOT_FOUND)) {
                message = "Page Not Found";
            } else {
                message = "Unknown Error";
            }
        }

        return new ResponseEntity<>(new JsonResponse(message), status);
    }
}

package com.gravitee.gravitee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ResourceNotCreatedException extends RuntimeException {
    public ResourceNotCreatedException(String message) {
        super(message);
    }
}

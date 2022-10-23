package org.selbowgreaser.service.parser;

import org.selbowgreaser.model.UserRequest;

public interface UserRequestParser {
    UserRequest parseRequest(String request);
}

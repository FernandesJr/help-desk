package com.gilfer.helpdesk.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError{

    public List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(){}

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String field, String message) {
        this.errors.add(new FieldMessage(field, message));
    }
}

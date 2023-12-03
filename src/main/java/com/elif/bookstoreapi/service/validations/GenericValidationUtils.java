package com.elif.bookstoreapi.service.validations;

import com.elif.bookstoreapi.core.exceptions.GenericValidationException;

public class GenericValidationUtils {

    public static void isIdNullForUpdate(Integer id) {
        if (id == null) {
            throw new GenericValidationException("Id can not be null");
        }
    }

}

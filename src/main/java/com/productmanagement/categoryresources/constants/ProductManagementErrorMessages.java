package com.productmanagement.categoryresources.constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ProductManagementErrorMessages {
        SUCCESS(HttpStatus.OK, "Success"),
        BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

        HttpStatus status;
        String message;
    }


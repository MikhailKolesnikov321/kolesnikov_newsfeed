package com.example.kolesnikov_advancedServer.validations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {
    UNKNOWN(0, ValidationConstants.UNKNOWN),
    USERNAME_SIZE_NOT_VALID(1, ValidationConstants.USERNAME_SIZE_NOT_VALID),
    ROLE_SIZE_NOT_VALID(2, ValidationConstants.ROLE_SIZE_NOT_VALID),
    EMAIL_SIZE_NOT_VALID(3, ValidationConstants.EMAIL_SIZE_NOT_VALID),
    MUST_NOT_BE_NULL(4, "must not be null"),
    USER_NOT_FOUND(5, ValidationConstants.USER_NOT_FOUND),
    TOKEN_NOT_PROVIDED(6, ValidationConstants.TOKEN_NOT_PROVIDED),
    UNAUTHORISED(7, "unauthorised"),
    USER_EMAIL_NOT_NULL(8, ValidationConstants.USER_EMAIL_NOT_NULL),
    USER_PASSWORD_NULL(9, ValidationConstants.USER_PASSWORD_NULL),
    USER_ROLE_NOT_NULL(10, ValidationConstants.USER_ROLE_NOT_NULL),
    NEWS_DESCRIPTION_SIZE(11, ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID),
    NEWS_DESCRIPTION_NOT_NULL(12, ValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT),
    NEWS_TITLE_SIZE(13, ValidationConstants.NEWS_TITLE_SIZE),
    NEWS_TITLE_NOT_NULL(14, ValidationConstants.NEWS_TITLE_NOT_NULL),
    PARAM_PAGE_NOT_NULL(15, ValidationConstants.PARAM_PAGE_NOT_NULL),
    PARAM_PER_PAGE_NOT_NULL(16, ValidationConstants.PARAM_PER_PAGE_NOT_NULL),
    USER_EMAIL_NOT_VALID(17, ValidationConstants.USER_EMAIL_NOT_VALID),
    PAGE_SIZE_NOT_VALID(18, ValidationConstants.PAGE_SIZE_NOT_VALID),
    PER_PAGE_MIN_NOT_VALID(19, ValidationConstants.PER_PAGE_MIN_NOT_VALID),
    PER_PAGE_MAX_NOT_VALID(19, ValidationConstants.PER_PAGE_MAX_NOT_VALID),
    EXCEPTION_HANDLER_NOT_PROVIDED(21, "Exception handler not provided"),
    REQUEST_IS_NOT_MULTIPART(22, "Current request is not a multipart request"),
    MAX_UPLOAD_SIZE_EXCEEDED(23, ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED),
    USER_AVATAR_NOT_NULL(24, ValidationConstants.USER_AVATAR_NOT_NULL),
    PASSWORD_NOT_VALID(25, ValidationConstants.PASSWORD_NOT_VALID),
    NEWS_NOT_FOUND(27, "news not found"),
    ID_MUST_BE_POSITIVE(29, ValidationConstants.ID_MUST_BE_POSITIVE),
    USER_ALREADY_EXISTS(30, "User already exists"),
    REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT(40, ValidationConstants.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT),
    REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT(41, ValidationConstants.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT),
    USER_NAME_HAS_TO_BE_PRESENT(43, ValidationConstants.USERNAME_HAS_TO_BE_PRESENT),
    TAGS_NOT_VALID(44, ValidationConstants.TAGS_NOT_VALID),
    NEWS_IMAGE_HAS_TO_BE_PRESENT(45, ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT),
    USER_WITH_THIS_EMAIL_ALREADY_EXIST(46, ValidationConstants.USER_WITH_THIS_EMAIL_ALREADY_EXIST),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(47, "Http request not valid"),
    AUTH_IS_NULL(48, "Cannot invoke \"org.springframework.security.core.Authentication.getPrincipal()\" because \"authentication\" is null");

    private static final Map<String, ErrorCodes> errorMap;

    static {
        errorMap = new HashMap<>();
        for (ErrorCodes v : ErrorCodes.values()) {
            errorMap.put(v.getMessage(), v);
        }
    }

    public static ErrorCodes findByMessage(String message) {
        return errorMap.get(message);
    }

    private final Integer code;
    private final String message;
}
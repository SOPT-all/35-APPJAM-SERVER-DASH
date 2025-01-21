package be.dash.dashserver.core.log;

public class LogForm {
    private static final String DELIMITER = ",\n    ";
    private static final String INDENT = "    ";

    public static final String REQUEST_LOGGING_FORM = "\n{\n" +
            INDENT + "\"httpStatus\": \"{}\"" + DELIMITER +
            "\"httpMethod\": \"{}\"" + DELIMITER +
            "\"requestUri\": \"{}\"" + DELIMITER +
            "\"tokenExists\": \"{}\"" + DELIMITER +
            "\"processingTimeMs\": \"{}\"\n" +
            "}";

    public static final String TRACE_LOGGING_FORM = "\n{\n" +
            INDENT + "\"Invoked Class\": \"{}\"" + DELIMITER +
            "\"Invoked Method\": \"{}\"\n" +
            "}";

    public static final String LOGIN_MEMBER_FORM = "\n{\n" +
            INDENT + "\"loginMemberId\": \"{}\"" + "}";

    public static final String ERROR_LOGGING_FORM = "\n{\n" +
            INDENT + "\"exceptionClass\": \"{}\"" + DELIMITER +
            "\"exceptionMessage\": \"{}\"\n" +
            "\"exceptionStackTrace\": \"{}\"\n" +
            "}";
}

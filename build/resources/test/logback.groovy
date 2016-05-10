appender ("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}
logger("root", DEBUG, ["STDOUT"])
logger("org.apache.http", DEBUG)
logger("org.apache.http.wire", DEBUG)

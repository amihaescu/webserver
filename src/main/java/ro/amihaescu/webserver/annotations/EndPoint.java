package ro.amihaescu.webserver.annotations;

import ro.amihaescu.webserver.constants.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EndPoint {

    HttpMethod method();
    String path();
}

package ro.amihaescu.webserver.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ro.amihaescu.webserver.constants.HttpMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MethodPath {

    @EqualsAndHashCode.Include
    private HttpMethod method;
    @EqualsAndHashCode.Include
    private String path;
}

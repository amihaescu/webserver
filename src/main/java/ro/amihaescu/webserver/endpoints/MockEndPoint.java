package ro.amihaescu.webserver.endpoints;

import com.google.common.collect.Lists;
import ro.amihaescu.webserver.annotations.EndPoint;
import ro.amihaescu.webserver.annotations.RestController;
import ro.amihaescu.webserver.constants.HttpMethod;

import java.util.List;

@RestController
public class MockEndPoint {

    @EndPoint(method = HttpMethod.GET, path = "/mock")
    public List<String> getSomeData() {
        return (List<String>) Lists.newArrayList(
                "First Element",
                "Second Element",
                "Third Element"
        );
    }
}

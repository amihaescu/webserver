package ro.amihaescu.webserver.endpoints;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ro.amihaescu.webserver.annotations.EndPoint;
import ro.amihaescu.webserver.annotations.RestController;
import ro.amihaescu.webserver.constants.HttpMethod;

import java.util.List;
import java.util.Map;

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

    @EndPoint(method = HttpMethod.GET, path = "/otherData")
    public Map<String, String> getSomeOtherData(){
        return ImmutableMap.<String,String> builder()
                .put("Key 1", "Value 1")
                .put("Key 2", "Value 2")
                .build();
    }
}

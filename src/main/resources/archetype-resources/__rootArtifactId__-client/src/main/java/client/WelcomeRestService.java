#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import ${package}.common.WelcomeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a convenience wrapper around all REST calls to the remote server. Other classes in the project can use this
 * to perform remote procedure calls without worrying about the messy business of URL and String mangling. Under the
 * covers this class uses Spring's RestTemplate to simplify the task of talking to the server. For more information on
 * Spring's RestTemplate see: http://blog.springsource.com/2009/03/27/rest-in-spring-3-resttemplate/
 */
public class WelcomeRestService {

    private static final Logger log = LoggerFactory.getLogger(WelcomeRestService.class);

    /**
     * This RestTemplate is a Spring helper class that simplifies the job of making remote calls REST onto the server.
     * It is marked as @Autowired, which means that Spring will magically wire in a RestTemplate at runtime when this
     * service is loaded from an ApplicationContext (see the SimpleRestAppFactory).
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This is a configuration property that allows us to define the base server URL to use at runtime. This is
     * ultimately loaded from the client.properties file but Spring allows us to hide a lot of this and we just deal
     * with a nice simple String here.
     * <p/>
     * By default the URL will point to 'http://localhost:8080/${parentArtifactId}-server' which should hit your
     * local web server if you run it using the default settings of this project. When deploying to a real world
     * environment, you should change the properties file to match your actual server URL.
     */
    private String serverUrl;

    /**
     * Constructs a new instance of this service so that it will provide access to the REST methods made available by
     * the server at the URL provided.
     *
     * @param serverUrl the base URL of the server to access, should be in normal URL form along the lines of
     *                  'http://localhost:8080/${parentArtifactId}-server'
     */
    public WelcomeRestService(String serverUrl) {
        log.info("WelcomeRestService is using server URL '{}'", serverUrl);
        this.serverUrl = serverUrl;
    }

    /**
     * Makes a remote REST call to the server, invoking the 'welcome' method. This handles the job of building the
     * appropriate server URL (passing the name as a parameter) and then unmarshaling the result. If there is any
     * problem connecting to the server or reading the response then an appropriate exception is thrown by this method.
     *
     * @param name the name of the person saying hello. This is passed to the server as part of the REST URL.
     * @return the response from the server. We could have gotten away with a simple String result here but it is more
     * interesting to show a regular Java object being returned.
     */
    public WelcomeMessage sayHello(String name) {
        log.info("Saying hello to server using name: {}", name);
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("name", name);
        WelcomeMessage welcomeMessage = restTemplate.getForObject(
                serverUrl + "/welcome?name={name}", WelcomeMessage.class, values);
        log.info("Server responded with: {}", welcomeMessage.getMessage());
        return welcomeMessage;
    }
}

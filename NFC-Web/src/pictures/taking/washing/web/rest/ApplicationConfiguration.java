package pictures.taking.washing.web.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api/v1")
public class ApplicationConfiguration extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ApplicationConfiguration() {
        //below line caused @Inject not to work. commented out
        //singletons.add(new PersonService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }


//    private Set<Object> singletons = new HashSet<Object>();
//
//    public ApplicationConfiguration() {
//        singletons.add(new HikesEndpoint());
//        singletons.add(new AuthenticationEndpoint());
//    }
//
//    @Override
//    public Set<Object> getSingletons() {
//        return singletons;
//    }

}

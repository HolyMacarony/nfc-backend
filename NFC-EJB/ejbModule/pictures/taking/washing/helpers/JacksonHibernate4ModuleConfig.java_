package pictures.taking.washing.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonHibernate4ModuleConfig implements ContextResolver<ObjectMapper> {

    private ObjectMapper objectMapper = new ObjectMapper() {
        private static final long serialVersionUID = 1L;

        {
            Hibernate4Module hibernate4Module = new Hibernate4Module();
            //hibernate4Module.configure(Hibernate4Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
            registerModule(hibernate4Module);
        }
    };

    @Override
    public ObjectMapper getContext(Class objectType) {
        return objectMapper;
    }
}
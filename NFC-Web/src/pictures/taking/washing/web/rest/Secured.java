package pictures.taking.washing.web.rest;

import pictures.taking.washing.persistence.enums.SecurityroleEnum;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the annotation will be used to decorate a filter class,
 * which implements ContainerRequestFilter,
 * allowing to intercept the request before it be handled by a resource method.
 */

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    SecurityroleEnum[] value() default {};
}

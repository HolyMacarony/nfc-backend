package pictures.taking.washing.ejb.constraints.annotations;

import pictures.taking.washing.ejb.constraints.validators.UniqueURLValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueURLValidator.class)
@Documented
public @interface UniqueURL {
    String message() default "{error_url_UNIQUE}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
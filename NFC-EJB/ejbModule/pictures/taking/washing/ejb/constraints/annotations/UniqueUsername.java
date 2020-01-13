package pictures.taking.washing.ejb.constraints.annotations;

import pictures.taking.washing.ejb.constraints.validators.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {
    String message() default "{error_username_UNIQUE}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

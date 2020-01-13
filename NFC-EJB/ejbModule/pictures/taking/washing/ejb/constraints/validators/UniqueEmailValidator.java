package pictures.taking.washing.ejb.constraints.validators;

import pictures.taking.washing.ejb.constraints.annotations.UniqueEmail;
import pictures.taking.washing.ejb.interfaces.UserDAO;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @EJB
    private UserDAO userDao;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // already injected UserDAO
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userDao.findByEmail(value) == null;
    }

}
package pictures.taking.washing.ejb.constraints.validators;

import pictures.taking.washing.ejb.constraints.annotations.UniqueUsername;
import pictures.taking.washing.ejb.interfaces.UserDAO;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @EJB
    private UserDAO userDao;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // already injected UserDAO
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userDao.findByUsername(value) == null;
    }

}

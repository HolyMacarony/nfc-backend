package pictures.taking.washing.ejb.constraints.validators;

import pictures.taking.washing.ejb.constraints.annotations.UniqueURL;
import pictures.taking.washing.ejb.interfaces.HikeDAO;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueURLValidator implements ConstraintValidator<UniqueURL, String> {

    @EJB
    private HikeDAO hikeDAO;

    @Override
    public void initialize(UniqueURL constraintAnnotation) {
        // already injected UserDAO
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hikeDAO.findByURL(value) == null;
    }

}
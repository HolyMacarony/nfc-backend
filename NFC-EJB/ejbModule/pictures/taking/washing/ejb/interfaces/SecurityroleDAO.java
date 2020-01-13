package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.Securityrole;

import java.util.List;

public interface SecurityroleDAO {
    Securityrole create(Securityrole Securityrole);

    Securityrole getSecurityrole(Long id);

    Securityrole getSecurityroleByName(String name);

    List<Securityrole> getAllSecurityroles();
}

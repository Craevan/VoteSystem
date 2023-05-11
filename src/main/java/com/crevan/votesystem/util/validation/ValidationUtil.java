package com.crevan.votesystem.util.validation;

import com.crevan.votesystem.HasId;
import com.crevan.votesystem.error.IllegalRequestDataException;
import com.crevan.votesystem.model.Role;
import com.crevan.votesystem.model.User;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ValidationUtil {
    public static void checkNew(final HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(final HasId bean, final int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkRoles(final User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }
    }
}

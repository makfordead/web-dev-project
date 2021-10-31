package com.web.dev.authentication.security.service;


import com.web.dev.authentication.security.repository.entity.Role;

public interface RoleService {

    Role save(final String name);
}

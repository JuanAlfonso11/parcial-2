package org.example.util;
import  io.javalin.security.RouteRole;

public enum RolesApp implements RouteRole{
    ROLE_ADMIN,
    ROLE_USER,
}

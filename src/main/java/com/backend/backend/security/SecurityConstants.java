package com.backend.backend.security;

public class SecurityConstants {

    // 1 day in milliseconds
    public static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000;

    // Use a strong, long secret in production
    public static final String JWT_SECRET = "R0uT4W1vZ2Zld0p3d0F3c2ZlcmFuZG9tU3VwZXJTZWNyZXQxMjM0NTY3ODkwMTIzNDU2Nzg5";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}

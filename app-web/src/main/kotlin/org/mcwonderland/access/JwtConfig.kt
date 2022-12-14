package org.mcwonderland.access

import com.auth0.jwt.algorithms.Algorithm

data class JwtConfig(
    val issuer: String,
    val algorithm: Algorithm
)

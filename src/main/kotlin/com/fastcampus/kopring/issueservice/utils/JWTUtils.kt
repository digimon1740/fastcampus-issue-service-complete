package com.fastcampus.kopring.issueservice.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.interfaces.DecodedJWT

object JWTUtils {

    fun decode(token: String, secret: String, issuer: String): DecodedJWT {
        val algorithm = HMAC256(secret)

        val verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()

        return verifier.verify(token)
    }
}
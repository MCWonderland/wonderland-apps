package org.mcwonderland.web.resource

import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.UserTokenService
import org.mcwonderland.web.Config
import javax.inject.Inject

open class ResourceTest {

    @Inject
    lateinit var tokenService: UserTokenService

    @Inject
    lateinit var config: Config

    fun ValidatableResponse.assertUnauthorized() = statusCode(401)!!
    fun ValidatableResponse.assertBadRequest() = statusCode(400)!!
    fun ValidatableResponse.assertOk() = statusCode(200)!!


    fun RequestSpecification.withToken(user: User): RequestSpecification {
        return cookie(config.tokenCookieKey, tokenService.encodeToken(user))
    }

}
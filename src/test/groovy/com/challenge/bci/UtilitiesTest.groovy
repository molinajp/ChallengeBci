package com.challenge.bci

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.challenge.bci.entity.UserEntity
import com.challenge.bci.util.Utilities
import spock.lang.Specification

class UtilitiesTest extends Specification {

    private UserEntity userEntity

    private String url

    def setup() {
        userEntity = new UserEntity()
        url = "test"
    }

    def "Test para crear token de acceso JWT" () {
        when:
        userEntity.setId(new UUID(1L, 2L))
        def result = Utilities.createJwtToken(userEntity, url)

        then:
        result instanceof String
    }

    def "Test para crear token JWT tira excepción" () {
        when:
        Utilities.createJwtToken(null, url)

        then:
        thrown NullPointerException
    }

    def "Test para decodear el token JWT OK" () {
        when:
        userEntity.setId(new UUID(1L, 2L))
        def result = Utilities.decodeToken(Utilities.createJwtToken(userEntity, url))

        then:
        result instanceof DecodedJWT
    }

    def "Test para decodear el token JWT tira excepción" () {
        when:
        Utilities.decodeToken("Token malformado")

        then:
        thrown JWTVerificationException
    }

}

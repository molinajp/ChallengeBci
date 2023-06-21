package com.challenge.bci

import com.challenge.bci.dto.user.UserDtoRequest
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class UserDtoTest extends Specification {

    private Validator validator

    private UserDtoRequest userDtoRequest

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
        userDtoRequest = new UserDtoRequest(null, "mail@mail.com", "Prueba01", null)
    }

    def "Test de UserDTO con problemas de contraseña por Mayúsculas"() {
        when:
        userDtoRequest.setPassword(password)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == expectedResult

        where:
        password   || expectedResult
        "prueba01" || 1
        "PrueBa02" || 1
        "pRue0bA3" || 1
    }

    def "Test de UserDTO con problemas de contraseña por Números"() {
        when:
        userDtoRequest.setPassword(password)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == expectedResult

        where:
        password     || expectedResult
        "Pruebaaa"   || 1
        "Pruebaa0"   || 1
        "Prueba012"  || 1
        "pr0uRbaa"   || 1
        "pr0u0Rb0aa" || 1
        "pr000uRbaa" || 1
        "00pr0uRbaa" || 1
        "000pruRbaa" || 1
    }

    def "Test de UserDTO con problemas de contraseña por Caracteres Especiales"() {
        when:
        userDtoRequest.setPassword(password)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == expectedResult

        where:
        password     || expectedResult
        "Prueba*01"  || 1
        "-Prueb3aa0" || 1
        "02Prueba!"  || 1
    }

    def "Test de UserDTO con problemas de contraseña por Longitud"() {
        when:
        userDtoRequest.setPassword(password)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == expectedResult

        where:
        password          || expectedResult
        "Prueb10"         || 1
        "Prrruee0eba1aaa" || 1
    }

    def "Test de UserDTO con problemas de contraseña nula"() {
        when:
        userDtoRequest.setPassword(null)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == 1
    }

    @Unroll
    def "Test de UserDTO con problemas de email por formato #email"() {
        when:
        userDtoRequest.setEmail(email)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == expectedResult

        where:
        email          || expectedResult
        "mailmail.com" || 1
        "mail@mailcom" || 1
        "mas.mail.com" || 1
    }

    def "Test de UserDTO con problemas de email nulo"() {
        when:
        userDtoRequest.setEmail(null)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.size() == 1
    }

    def "Test de UserDTO OK"() {
        when:
        userDtoRequest.setPassword(password)
        userDtoRequest.setEmail(email)
        Set<ConstraintViolation<UserDtoRequest>> violations = validator.validate(userDtoRequest)

        then:
        violations.isEmpty()

        where:
        email                   || password
        "test@gmail.com"        || "Passw0rd1"
        "test2@hotmail.com"     || "alGo9por7que"
        "test3@globallogic.com" || "1asegr6T"
    }

}

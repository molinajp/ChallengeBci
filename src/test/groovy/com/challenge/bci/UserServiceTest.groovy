package com.challenge.bci

import com.challenge.bci.dto.phone.PhoneDtoRequest
import com.challenge.bci.dto.user.UserDtoRequest
import com.challenge.bci.entity.PhoneEntity
import com.challenge.bci.entity.UserEntity
import com.challenge.bci.exception.TokenNotValidException
import com.challenge.bci.exception.UserEmailNotUniqueException
import com.challenge.bci.repository.UserRepository
import com.challenge.bci.serviceimpl.UserServiceImpl
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification
import spock.lang.Subject

class UserServiceTest extends Specification {

    @Subject
    private UserServiceImpl userService

    private UserRepository userRepository = Mock()

    private PasswordEncoder passwordEncoder = Mock()

    private UserEntity user

    private UserDtoRequest userDto

    def setup() {
        userService = new UserServiceImpl(userRepository, passwordEncoder)

        HashSet<PhoneDtoRequest> phonesDto = new HashSet<>()
        phonesDto.add(new PhoneDtoRequest())
        HashSet<PhoneEntity> phonesEntity = new HashSet<>()
        phonesEntity.add(new PhoneEntity())

        String name = "Juan"
        String email = "mail@mail.com"
        String password = "12Pasdfg"
        Date date = new Date()

        user = new UserEntity(UUID.randomUUID(), date, date, name, email, password, phonesEntity, true)
        userDto = new UserDtoRequest(name, email, password, phonesDto)
    }

    def "Test de método initAndSaveUser que lanza excepción"() {
        given: "El mail ya está usado por otro usuario"
        userRepository.findByEmail(_) >> user

        when: "cuando se llama el método"
        userService.initAndSaveUser(userDto)

        then: "se lanza la excepción correspondiente"
        thrown UserEmailNotUniqueException
    }

    def "Test de método initAndSaveUser OK"() {
        given: "todos las condiciones para crear correctamente un usuario"
        userRepository.findByEmail(_) >> null
        passwordEncoder.encode(_) >> userDto.getPassword()
        userRepository.save(_) >> user
        MockHttpServletRequest request = new MockHttpServletRequest()
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request))

        when: "cuando se llama el método"
        def result = userService.initAndSaveUser(userDto)

        then: "se deveulven todos los datos correctamente"
        with(result) {
            id == user.getId()
            email == user.getEmail()
            password == user.getPassword()
            name == user.getName()
            isActive() == user.isActive()
        }
    }

    def "Test de login incorrecto por token no válido" () {
        given: "Un token JWT que devuelve un usuario no existente"
        //token generado para tests
        def token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqcm9ja2V0QGV4YW1wbGUuY29tIiwiaWF0IjoxNjg2NjYxMzIzLCJleHAiOjE3MTgxOTczMjMsImF1ZCI6Ind3dy5leGFtcGxlLmNvbSIsInN1YiI6IjMxZjkwNWM4LTA5ZWEtMTFlZS1iZTU2LTAyNDJhYzEyMDAwMiIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.VSPedpsPEf0H2K3hM7eBs-uJCjojbDrfbfQIx6a5Juc"
        userRepository.findById(_) >> Optional.empty()

        when: "se intenta loguear"
        userService.loginUser(token)

        then: "lanza una excepción"
        thrown TokenNotValidException
    }

    def "Test de login correcto" () {
        given: "Un token JWT que devuelve un usuario existente"
        //token generado para tests
        def token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqcm9ja2V0QGV4YW1wbGUuY29tIiwiaWF0IjoxNjg2NjYxMzIzLCJleHAiOjE3MTgxOTczMjMsImF1ZCI6Ind3dy5leGFtcGxlLmNvbSIsInN1YiI6IjMxZjkwNWM4LTA5ZWEtMTFlZS1iZTU2LTAyNDJhYzEyMDAwMiIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.VSPedpsPEf0H2K3hM7eBs-uJCjojbDrfbfQIx6a5Juc"
        userRepository.findById(_) >> Optional.of(user)
        MockHttpServletRequest request = new MockHttpServletRequest()
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request))

        when: "se intenta loguear"
        def result = userService.loginUser(token)

        then: "se deveulven todos los datos correctamente"
        with(result) {
            id == user.getId()
            email == user.getEmail()
            password == user.getPassword()
            name == user.getName()
            isActive() == user.isActive()
        }
    }
}
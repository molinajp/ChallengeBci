package com.challenge.bci

import com.challenge.bci.entity.UserEntity
import com.challenge.bci.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class UserRepositoryTest extends Specification{

    @Autowired
    private UserRepository userRepository

    def setup(){
        userRepository.save(new UserEntity(null, new Date(), new Date(), null, "mail.m@mail.com", "12Pasdfg", null, true))
        userRepository.save(new UserEntity(null, new Date(), new Date(), null, "mail@mail.com", "12Pasdfg", null, true))
        userRepository.save(new UserEntity(null, new Date(), new Date(), null, "asdf@test.com", "12Pasdfg", null, true))
    }

    def "Test parametrizado de obtener usuario por mail que devuelva nulo" () {
        when:
        def result = userRepository.findByEmail(email)

        then:
        result == null

        where:
        email << ["mai@mail.com", " ", "", "mail.com", "mail@mailcom"]
    }

    def "Test parametrizado de obtener usuario correctamente" () {
        when:
        def result = userRepository.findByEmail(email)

        then:
        result instanceof UserEntity

        where:
        email << ["mail.m@mail.com", "mail@mail.com", "asdf@test.com"]
    }

}

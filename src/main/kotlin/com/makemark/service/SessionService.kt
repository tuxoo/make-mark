package com.makemark.service

import com.makemark.config.property.ApplicationProperty
import com.makemark.config.property.SessionProperty
import org.springframework.stereotype.Service

@Service
class SessionService(
    val applicationProperty: ApplicationProperty,
    val sessionProperty: SessionProperty
) {

//    fun createSession(user: User): UUID =
//        with(sessionRepository.findAllByUser(user)) {
//            if (size >= sessionProperty.max) {
//                sessionRepository.deleteAll(this)
//            }
//        }.run {
//            sessionRepository.insert(
//                Session(
//                    user = user,
//                    expiresAt = Instant.now().plus(applicationProperty.accessTokenTTL)
//                )
//            ).refreshToken
//        }
}
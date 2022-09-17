package com.makemark.config.security

import com.makemark.model.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AppUserDetails(
    private val id: String,
    private val login: String,
    private val isEnabled: Boolean,
    private val grantedAuthorities: MutableCollection<out GrantedAuthority>
) : UserDetails {

    companion object {
        fun toUserDetails(user: User) =
            with(user)
            {
                AppUserDetails(
                    id = id.toString(),
                    login = email,
                    isEnabled = isEnabled,
                    grantedAuthorities = mutableListOf(SimpleGrantedAuthority(user.role.name))
                )
            }
    }

    fun getId(): String = id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = grantedAuthorities

    override fun getPassword(): String = ""

    override fun getUsername(): String = login

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isEnabled
}
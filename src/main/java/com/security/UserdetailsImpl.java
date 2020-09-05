package com.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.model.Users;

import net.bytebuddy.agent.builder.AgentBuilder.Identified.Extendable;

public class UserdetailsImpl implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    transient Users u;

    public UserdetailsImpl(Users u) {
        super();
        this.u = u;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> a = new ArrayList<>();

        this.u.getAuth().forEach(r -> {
            GrantedAuthority g = new SimpleGrantedAuthority("ROLE_" + r);
            a.add(g);
        });

        System.out.println(a);

        return a;
    }

    @Override
    public String getPassword() {
        return u.getPass();
    }

    @Override
    public String getUsername() {
        return u.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return u.isEnable();
  }

}

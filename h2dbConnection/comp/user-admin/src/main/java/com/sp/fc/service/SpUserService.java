package com.sp.fc.service;

import com.sp.fc.domain.SpAuthority;
import com.sp.fc.domain.SpUser;
import com.sp.fc.repository.SpUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class SpUserService implements UserDetailsService {
    private final SpUserRepository spUserRepository;

    public SpUserService(SpUserRepository spUserRepository) {
        this.spUserRepository = spUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return spUserRepository.findSpUserByEmail(username)
                .orElseThrow(() ->new UsernameNotFoundException(username));
    }

    public Optional<SpUser> findUser(String email){
        return spUserRepository.findSpUserByEmail(email);
    }
    public SpUser save(SpUser user){
        return spUserRepository.save(user);
    }
    public void addAuthority(Long userId,String authority){
        spUserRepository.findById(userId).ifPresent(user->{
            SpAuthority newRole = new SpAuthority(user.getUserId(),authority);
            if (user.getAuthorities() == null){
                HashSet<SpAuthority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }else if(!user.getAuthorities().contains(newRole)){
                HashSet<SpAuthority> authorities = new HashSet<>();
                authorities.addAll(user.getAuthorities());
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }
        });
    }

}

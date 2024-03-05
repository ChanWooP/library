package com.cwpark.library.config.security;

import com.cwpark.library.dao.UserDao;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.UserRepository;
import com.cwpark.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);

        if(user == null) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다");
        }

        List<String> authorityList = new ArrayList<>();
        authorityList.add(user.getUserAuthority().toString());

        return new Account(user.getUserId(), user.getUserPassword(), authorityList, user.getUserLoginFailCnt());
    }

}

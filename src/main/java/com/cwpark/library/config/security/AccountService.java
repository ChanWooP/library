package com.cwpark.library.config.security;

import com.cwpark.library.config.CustomRequest;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomRequest customRequest;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String uuid = UUID.randomUUID().toString();

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다"));

        log.info("[{}] 로그인 시도 IP=[{}], TIME=[{}], ID=[{}]", uuid, customRequest.getUserIp(), LocalDateTime.now(), username);

        List<String> authorityList = new ArrayList<>();
        authorityList.add(user.getUserAuthority().toString());

        return new Account(user.getUserId(), user.getUserPassword(), authorityList, user.getUserLoginFailCnt());
    }

}

package com.cwpark.library.service;

import com.cwpark.library.data.entity.RememberMeToken;
import com.cwpark.library.repository.RememberMeTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RememberMeTokenService implements PersistentTokenRepository {

    private final RememberMeTokenRepository rememberMeTokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMeToken newToken = new RememberMeToken(token);
        rememberMeTokenRepository.save(newToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Optional<RememberMeToken> rememberMeToken = rememberMeTokenRepository.findById(series);
        rememberMeToken.ifPresent((t) -> {
            t.setLoginToken(tokenValue);
            t.setLoginTokenLastUsed(lastUsed);
            rememberMeTokenRepository.save(t);
        });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<RememberMeToken> rememberMeToken = rememberMeTokenRepository.findById(seriesId);
        return rememberMeToken.map((t) ->
                new PersistentRememberMeToken(t.getLoginTokenUserName(), t.getLoginTokenSeries(), t.getLoginToken(), t.getLoginTokenLastUsed()))
                .orElse(null);
    }

    @Override
    public void removeUserTokens(String username) {
        List<RememberMeToken> rememberMeTokens = rememberMeTokenRepository.findByLoginTokenUserName(username);

        rememberMeTokenRepository.deleteAll(rememberMeTokens);
    }
}

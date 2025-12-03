package com.tropisure.registrationserviceapi.service;




import com.tropisure.registrationserviceapi.entity.AgentInvitationToken;
import com.tropisure.registrationserviceapi.entity.TokenUsageLog;
import com.tropisure.registrationserviceapi.repository.AgentInvitationTokenRepository;
import com.tropisure.registrationserviceapi.repository.TokenUsageLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AgentInvitationTokenRepository tokenRepository;
    private final TokenUsageLogRepository tokenUsageLogRepository;

    public void logTokenUsage(AgentInvitationToken token, String eventType) {
        TokenUsageLog log = TokenUsageLog.builder()
                .token(token)
                .eventType(eventType)
                .eventTime(LocalDateTime.now())
                .build();
        tokenUsageLogRepository.save(log);
    }
}

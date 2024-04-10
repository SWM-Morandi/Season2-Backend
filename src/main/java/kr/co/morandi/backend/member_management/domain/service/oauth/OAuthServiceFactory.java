package kr.co.morandi.backend.member_management.domain.service.oauth;

import kr.co.morandi.backend.member_management.application.service.oauth.OAuthService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthServiceFactory {
    private final Map<String, OAuthService> serviceMap;
    public OAuthServiceFactory(List<OAuthService> oAuthServices) {
        this.serviceMap = oAuthServices.stream()
                .collect(Collectors.toMap(OAuthService::getType, Function.identity()));
    }
    public OAuthService getServiceByType(String type) {
        return serviceMap.get(type);
    }
}

package org.apereo.cas.web;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apereo.cas.OidcConstants;
import org.apereo.cas.OidcIdTokenGenerator;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.OidcRegisteredService;
import org.apereo.cas.support.oauth.OAuthResponseTypes;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.web.OAuth20AccessTokenResponseGenerator;
import org.apereo.cas.ticket.accesstoken.AccessToken;
import org.apereo.cas.ticket.refreshtoken.RefreshToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is {@link OidcAccessTokenResponseGenerator}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class OidcAccessTokenResponseGenerator extends OAuth20AccessTokenResponseGenerator {
    private final OidcIdTokenGenerator idTokenGenerator;

    public OidcAccessTokenResponseGenerator(final OidcIdTokenGenerator idTokenGenerator) {
        this.idTokenGenerator = idTokenGenerator;
    }

    @Override
    protected void generateJsonInternal(final HttpServletRequest request, final HttpServletResponse response,
                                        final JsonGenerator jsonGenerator,
                                        final AccessToken accessTokenId,
                                        final RefreshToken refreshTokenId,
                                        final long timeout,
                                        final Service service,
                                        final OAuthRegisteredService registeredService,
                                        final OAuthResponseTypes responseType) throws Exception {

        super.generateJsonInternal(request, response, jsonGenerator, accessTokenId,
                refreshTokenId, timeout, service, registeredService, responseType);
        final OidcRegisteredService oidcRegisteredService = (OidcRegisteredService) registeredService;
        final String idToken = this.idTokenGenerator.generate(request, response, accessTokenId,
                timeout, responseType, oidcRegisteredService);
        jsonGenerator.writeStringField(OidcConstants.ID_TOKEN, idToken);
    }

}


package org.apereo.cas.validation;

/**
 * Validation specification for the CAS 2.0 protocol. This specification extends
 * the Cas20ProtocolValidationSpecification, checking for the presence of
 * renew=true and if requested, succeeding only if ticket validation is
 * occurring from a new login. Additionally, this specification will not accept
 * proxied authentications.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
public class Cas20WithoutProxyingValidationSpecification extends AbstractCasProtocolValidationSpecification {

    /**
     * Instantiates a new cas20 without proxying validation specification.
     */
    public Cas20WithoutProxyingValidationSpecification() {
        super();
    }

    /**
     * Instantiates a new cas20 without proxying validation specification.
     *
     * @param renew the renew
     */
    public Cas20WithoutProxyingValidationSpecification(final boolean renew) {
        super(renew);
    }

    @Override
    protected boolean isSatisfiedByInternal(final Assertion assertion) {
        logger.debug("Number of chained authentications in the assertion [{}]", assertion.getChainedAuthentications().size());
        return assertion.getChainedAuthentications().size() == 1;
    }
}

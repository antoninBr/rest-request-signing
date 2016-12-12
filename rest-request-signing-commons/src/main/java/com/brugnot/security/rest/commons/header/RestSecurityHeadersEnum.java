package com.brugnot.security.rest.commons.header;

/**
 * Created by Antonin on 27/11/2016.
 */
public enum RestSecurityHeadersEnum implements RestSecurityHeaders{

    REST_CANONICAL_REQUEST("Rest-Canonical-Request",true),
    REST_CANONICAL_REQUEST_KEY("Rest-Canonical-Request-Key",true),
    REST_CANONICAL_REQUEST_HASH_ALGORITHM("Rest-Canonical-Request-Hash-Algorithm",true),
    REST_PAYLOAD_HASH_ALGORITHM("Rest-Payload-Hash-Algorithm",false),
    REST_SIGNATURE_DATE("Rest-Signature-Date",false),
    REST_SIGNATURE_USER("Rest-Signature-user",true),
    REST_CANONICAL_REQUEST_BUILDER_VERSION("Rest-Canonical-Request-Builder-Version",false);


    private String normalizedName;
    private boolean isMandatory;

    RestSecurityHeadersEnum(String normalizedName, boolean isMandatory) {
        this.normalizedName = normalizedName;
        this.isMandatory = isMandatory;
    }

    public String getNormalizedName() {
        return this.normalizedName;
    }

    public boolean isMandatory() {
        return this.isMandatory;
    }


}

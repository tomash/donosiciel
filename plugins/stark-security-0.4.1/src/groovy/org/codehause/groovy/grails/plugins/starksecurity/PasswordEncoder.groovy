package org.codehaus.groovy.grails.plugins.starksecurity

import java.security.MessageDigest
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex

class PasswordEncoder {

	static encode = { str, algorithm = 'SHA-256', encodeBase64 = true ->
		MessageDigest md = MessageDigest.getInstance(algorithm)
		md.update(str.getBytes('UTF-8'))
        byte[] encodedBytes = md.digest()
        if (encodeBase64) {
            encodedBytes = Base64.encodeBase64(encodedBytes)
        } else {
            encodedBytes = Hex.encodeHex(encodedBytes)
        }
        
        return new String(encodedBytes)
	}

}
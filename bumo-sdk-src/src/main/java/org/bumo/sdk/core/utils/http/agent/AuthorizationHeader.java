package org.bumo.sdk.core.utils.http.agent;


/***
 * Http Authorization Header；
 * 
 * @author haiq
 *
 */
public class AuthorizationHeader implements RequestHeader{

	/**@see org.bumo.sdk.core.utils.http.agent.AuthorizationAlgs*/
	private String alg;				// Algorithm type
	
	private String senderName;		// User name registered by the developer
	 
	private String secretKey;		// The secret key
	
	public AuthorizationHeader(){
		
	}
	
	public AuthorizationHeader(String senderName, String secretKey){
		this(AuthorizationAlgs.DEFAULT, senderName, secretKey);
	}
	
	public AuthorizationHeader(String alg, String senderName, String secretKey){
		this.alg = alg;
		this.senderName = senderName;
		this.secretKey = secretKey;
	}

	public String getAlg() {
		return alg;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getSecretKey() {
		return secretKey;
	}

	@Override
	public String getName() {
		return "Authorization";
	}

	@Override
	public String getValue() {
		StringBuilder authBuilder = new StringBuilder();
		authBuilder.append(alg).append(" ").append(senderName).append(":")
				.append(secretKey);
		return authBuilder.toString();
	}
	
	
}

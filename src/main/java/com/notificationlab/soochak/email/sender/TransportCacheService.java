package com.notificationlab.soochak.email.sender;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Transport;

final class TransportCacheService {

	private static final Map<String, Transport> transportCache;
	
	static {
		transportCache = new HashMap<>();
	}
	
	private TransportCacheService() {}
	
	public static Transport getTransport(String key) {
		return transportCache.get(key);
	}

	public static  <T extends Transport>  void putTransport(String key, T transport) {
			transportCache.put(key,transport);
	}
}

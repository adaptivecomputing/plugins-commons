package com.adaptc.mws.plugins.testing

import spock.lang.*

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

import static com.adaptc.mws.plugins.testing.MockSslService.*

@Unroll
class MockSslServiceSpec extends Specification {
	MockSslService service

	def setup() {
		service = new MockSslService()
	}

	def "Lenient socket factory"() {
		given:
		SSLSocketFactory socketFactory = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers ->
			assert keyManagers==null
			assert trustManagers.size()==1
			return socketFactory
		}
		service.metaClass.getLenientTrustManagers = { ->
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getLenientSocketFactory()

		then:
		result==socketFactory
	}

	def "Lenient socket factory (http client)"() {
		given:
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getHttpClientSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers,
																 org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier=null ->
			assert (hostnameVerifier instanceof LenientHttpClientX509HostnameVerifier)==useHostnameVerifier
			assert keyManagers==null
			assert trustManagers.size()==1
			return socketFactory
		}
		service.metaClass.getLenientTrustManagers = { ->
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getLenientHttpClientSocketFactory(useHostnameVerifier)

		then:
		result==socketFactory

		where:
		useHostnameVerifier << [true, false]
	}

	def "Client certificate"() {
		given:
		SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		service.metaClass.getSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers ->
			assert keyManagers.size()==1
			assert trustManagers==null
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="certificate"
			assert alias=="alias"
			assert key==null
			assert password==null
			return (KeyManager[])[keyManager].toArray()
		}

		when:
		def result = service.getSocketFactory("certificate", "alias")

		then:
		result==socketFactory
	}

	def "Client certificate (http client)"() {
		given:
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		service.metaClass.getHttpClientSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers,
																 org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier=null ->
			assert !hostnameVerifier
			assert keyManagers.size()==1
			assert trustManagers==null
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="certificate"
			assert alias=="alias"
			assert key==null
			assert password==null
			return (KeyManager[])[keyManager].toArray()
		}

		when:
		def result = service.getHttpClientSocketFactory("certificate", "alias")

		then:
		result==socketFactory
	}

	def "Client certificate with key"() {
		given:
		SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		service.metaClass.getSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers ->
			assert keyManagers.size()==1
			assert trustManagers==null
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="certificate"
			assert alias=="alias"
			assert key=="key"
			assert password=="password"
			return (KeyManager[])[keyManager].toArray()
		}

		when:
		def result = service.getSocketFactory("certificate", "alias", "key", "password")

		then:
		result==socketFactory
	}

	def "Client certificate with key (http client)"() {
		given:
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		service.metaClass.getHttpClientSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers,
																 org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier=null ->
			assert !hostnameVerifier
			assert keyManagers.size()==1
			assert trustManagers==null
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="certificate"
			assert alias=="alias"
			assert key=="key"
			assert password=="password"
			return (KeyManager[])[keyManager].toArray()
		}

		when:
		def result = service.getHttpClientSocketFactory("certificate", "alias", "key", "password")

		then:
		result==socketFactory
	}

	def "Server certificate"() {
		given:
		SSLSocketFactory socketFactory = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers ->
			assert trustManagers.size()==1
			assert keyManagers==null
			return socketFactory
		}
		service.metaClass.getTrustManagers = { String cert ->
			assert cert=="certificate"
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getSocketFactory("certificate")

		then:
		result==socketFactory
	}

	def "Server certificate (http client)"() {
		given:
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getHttpClientSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers,
																 org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier=null ->
			assert !hostnameVerifier
			assert trustManagers.size()==1
			assert keyManagers==null
			return socketFactory
		}
		service.metaClass.getTrustManagers = { String cert ->
			assert cert=="certificate"
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getHttpClientSocketFactory("certificate")

		then:
		result==socketFactory
	}

	def "Client and server certificates"() {
		given:
		SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers ->
			assert keyManagers.size()==1
			assert trustManagers.size()==1
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="clientCert"
			assert alias=="alias"
			assert key==null
			assert password==null
			return (KeyManager[])[keyManager].toArray()
		}
		service.metaClass.getTrustManagers = { String cert ->
			assert cert=="serverCert"
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getSocketFactory("clientCert", "alias", "serverCert")

		then:
		result==socketFactory
	}

	def "Client and server certificates (http client)"() {
		given:
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getHttpClientSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers,
																 org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier=null ->
			assert !hostnameVerifier
			assert keyManagers.size()==1
			assert trustManagers.size()==1
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="clientCert"
			assert alias=="alias"
			assert key==null
			assert password==null
			return (KeyManager[])[keyManager].toArray()
		}
		service.metaClass.getTrustManagers = { String cert ->
			assert cert=="serverCert"
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getHttpClientSocketFactory("clientCert", "alias", "serverCert")

		then:
		result==socketFactory
	}

	def "Client with key and server certificates"() {
		given:
		SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers ->
			assert keyManagers.size()==1
			assert trustManagers.size()==1
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="clientCert"
			assert alias=="alias"
			assert key=="key"
			assert password=="password"
			return (KeyManager[])[keyManager].toArray()
		}
		service.metaClass.getTrustManagers = { String cert ->
			assert cert=="serverCert"
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getSocketFactory("clientCert", "alias", "key", "password", "serverCert")

		then:
		result==socketFactory
	}

	def "Client with key and server certificates (http client)"() {
		given:
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = Mock()
		KeyManager keyManager = Mock()
		TrustManager trustManager = Mock()
		service.metaClass.getHttpClientSocketFactoryInternal = { KeyManager[] keyManagers, TrustManager[] trustManagers,
																 org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier=null ->
			assert !hostnameVerifier
			assert keyManagers.size()==1
			assert trustManagers.size()==1
			return socketFactory
		}
		service.metaClass.getKeyManagers = { String cert, String alias, String key, String password ->
			assert cert=="clientCert"
			assert alias=="alias"
			assert key=="key"
			assert password=="password"
			return (KeyManager[])[keyManager].toArray()
		}
		service.metaClass.getTrustManagers = { String cert ->
			assert cert=="serverCert"
			return (TrustManager[])[trustManager].toArray()
		}

		when:
		def result = service.getHttpClientSocketFactory("clientCert", "alias", "key", "password", "serverCert")

		then:
		result==socketFactory
	}

	def "Get socket factory internal"() {
		when:
		def socketFactory = service.getSocketFactoryInternal(null, null)

		then:
		socketFactory instanceof SSLSocketFactory
	}

	def "Get http client socket factory internal"() {
		when: "No extra param for lenient hostname verifier"
		def socketFactory = service.getHttpClientSocketFactoryInternal(null, null)

		then:
		socketFactory instanceof org.apache.http.conn.ssl.SSLSocketFactory
		!(socketFactory.hostnameVerifier instanceof LenientHttpClientX509HostnameVerifier)

		when: "Do not use lenient hostname verifier"
		socketFactory = service.getHttpClientSocketFactoryInternal(null, null, null)

		then:
		socketFactory instanceof org.apache.http.conn.ssl.SSLSocketFactory
		!(socketFactory.hostnameVerifier instanceof LenientHttpClientX509HostnameVerifier)

		when: "Use lenient hostname verifier"
		org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier = Mock()
		socketFactory = service.getHttpClientSocketFactoryInternal(null, null, hostnameVerifier)

		then:
		socketFactory instanceof org.apache.http.conn.ssl.SSLSocketFactory
		socketFactory.hostnameVerifier==hostnameVerifier
	}

	def "Get certificate file #fileName"() {
		given:
		MockSslService service = new MockSslService(new File("/test"))

		expect:
		new File(fullPath)==service.getCertificateFile(fileName)

		where:
		fileName					| fullPath
		ABSOLUTE_PATH_ROOT+"cert"	| ABSOLUTE_PATH_ROOT+"cert"
		File.separator+"cert"		| File.separator+"cert"
		"cert"						| File.separator+"test/cert"
	}

	def "Get trust managers"() {
		given:
		def oldConstructor = FileInputStream.metaClass.retrieveConstructor([File.class] as Class[])
		FileInputStream.metaClass.constructor = { File file ->
			assert file.name=="certificate"
			return oldConstructor.newInstance(new File(this.class.getResource("/certs/server.pem").toURI()))
		}

		when:
		TrustManager[] trustManagers = service.getTrustManagers("certificate")

		then:
		trustManagers.size()==1
	}

	def "Get lenient trust managers"() {
		when:
		TrustManager[] trustManagers = service.getLenientTrustManagers()

		then:
		trustManagers.size()==1
		!trustManagers[0].getAcceptedIssuers()

		when:
		trustManagers[0].checkClientTrusted(null, null)
		trustManagers[0].checkServerTrusted(null, null)

		then:
		notThrown(Exception)
	}

	def "Get key managers"() {
		given:
		File.metaClass.getBytes = { ->
			return this.class.getResourceAsStream("/certs/client.pem").bytes
		}

		when: "Uses the certificate's alias by default"
		KeyManager[] keyManagers = service.getKeyManagers("cert", null, null, null)

		then:
		keyManagers.size()==1
		keyManagers[0] instanceof AliasForcingKeyManager

		and:
		keyManagers[0].chooseClientAlias(null, null, null)=="root"

		when: "Override certificate alias to use"
		keyManagers = service.getKeyManagers("cert", "myAlias", null, null)

		then:
		keyManagers.size()==1
		keyManagers[0] instanceof AliasForcingKeyManager

		and:
		keyManagers[0].chooseClientAlias(null, null, null)=="myAlias"
	}

	def "Use client private key"() {
		given:
		File.metaClass.getBytes = { ->
			return this.class.getResourceAsStream("/certs/${delegate.name}").bytes
		}

		when: "Use an unencrypted private key"
		KeyManager[] keyManagers = service.getKeyManagers("client-cert.pem", null, "client-key.pem", null)

		then:
		keyManagers.size()==1
		keyManagers[0] instanceof AliasForcingKeyManager

		and:
		keyManagers[0].chooseClientAlias(null, null, null)=="root"

		when: "Use an encrypted private key"
		keyManagers = service.getKeyManagers("client-encrypted-cert.pem", null, "client-encrypted-key.pem", "password")

		then:
		keyManagers.size()==1
		keyManagers[0] instanceof AliasForcingKeyManager

		and:
		keyManagers[0].chooseClientAlias(null, null, null)=="root"
	}

	def "Get lenient hostname verifier"() {
		when:
		def hostnameVerifier = service.getLenientHostnameVerifier()

		then:
		hostnameVerifier.verify(null, null)==true
	}
}

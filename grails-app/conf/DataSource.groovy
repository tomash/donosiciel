dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost/donosiciel_development?useUnicode=true&characterEncoding=UTF-8"
      username = "root"
      password = "root"
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost/donosiciel_test?useUnicode=true&characterEncoding=UTF-8"
      username = "root"
      password = "root"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://ilf.if.pw.edu.pl:3306/donosiciel?useUnicode=true&characterEncoding=UTF-8"
      //url = "jdbc:mysql://localhost/donosiciel_development?useUnicode=true&characterEncoding=UTF-8"
      username = "donosiciel"
      password = "donosiciel"
		}
	}
}

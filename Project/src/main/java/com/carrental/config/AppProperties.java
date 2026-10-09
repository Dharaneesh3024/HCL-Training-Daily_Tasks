package com.carrental.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app")
public class AppProperties {
	 private String supportEmail;
	    private String name;
	    private String version;
		public String getSupportEmail() {
			return supportEmail;
		}
		public void setSupportEmail(String supportEmail) {
			this.supportEmail = supportEmail;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
	    
}

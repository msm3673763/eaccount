package com.ucsmy.eaccount.pay.config;

import com.ucsmy.core.configuration.InitializationConfig;
import com.ucsmy.core.configuration.RedisConfig;
import com.ucsmy.core.configuration.SecurityConfig;
import com.ucsmy.core.configuration.SerialNumberConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfig implements InitializationConfig, SerialNumberConfig, RedisConfig, SecurityConfig {
}

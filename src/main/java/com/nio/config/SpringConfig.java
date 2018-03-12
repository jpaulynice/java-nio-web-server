package com.nio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Spring configuration for the application. Used by spring to find beans to
 * manage and their dependencies.
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
@Configuration
@ComponentScan(
        basePackages = { "com.nio.http" },
        excludeFilters = @Filter(
                type = FilterType.REGEX,
                pattern = "com.nio.config.*"))
public class SpringConfig {}

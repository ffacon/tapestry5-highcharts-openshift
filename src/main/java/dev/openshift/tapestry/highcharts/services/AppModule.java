package dev.openshift.tapestry.highcharts.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.apache.tapestry5.ioc.services.LoggingAdvisor;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
public class AppModule {

	/**
	 * Make bind() calls on the binder object to define most IoC services. Use
	 * service builder methods (example below) when the implementation is
	 * provided inline, or requires more initialization than simply invoking the
	 * constructor
	 * 
	 * @param pBinder
	 *            to use
	 */
	public static void bind(final ServiceBinder pBinder) {
		// This next line addresses an issue affecting GlassFish and JBoss - see http://blog.progs.be/?p=52
		javassist.runtime.Desc.useContextClassLoader = true;
	}
	
	// Tell Tapestry how to handle JBoss 7's classpath URLs - JBoss uses a "virtual file system".
	// See "Running Tapestry on JBoss" in http://wiki.apache.org/tapestry/Tapestry5HowTos .

	@SuppressWarnings("rawtypes")
	public static void contributeServiceOverride(MappedConfiguration<Class, Object> configuration) {
		configuration.add(ClasspathURLConverter.class, new ClasspathURLConverterJBoss7());
	}

	/**
	 * AOP adviser for @LOG annotation, which we want to have for all service
	 * and dao methods. This means that it will log the entry parameters and
	 * exit parameters as debug log which is usefull for debugging purposes.and
	 * we do not need to specify in each method a log statement.
	 * 
	 * @param pLoggingAdvisor
	 *            LoggingAdvisor
	 * @param pLogger
	 *            slf4j with log4j logger.
	 * @param pReceiver
	 *            MethodAdviceReceiver
	 */
	@Match({ "*Service*", "*Dao*" })
	public static void adviseLogging(final LoggingAdvisor pLoggingAdvisor,
			final Logger pLogger, final MethodAdviceReceiver pReceiver) {
		pLoggingAdvisor.addLoggingAdvice(pLogger, pReceiver);
	}

	/**
	 * @param pConfiguration
	 *            to use
	 */
	public static void contributeApplicationDefaults(
			final MappedConfiguration<String, Object> pConfiguration) {
		// Contributions to ApplicationDefaults will override any contributions
		// to
		// FactoryDefaults (with the same key). Here we're restricting the
		// supported
		// locales to just "en" (English). As you add localised message catalogs
		// and other assets,
		// you can extend this list of locales (it's a comma separated series of
		// locale names;
		// the first locale name is the default when there's no reasonable
		// match).
		pConfiguration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
		pConfiguration.add(SymbolConstants.MINIFICATION_ENABLED, false);
		pConfiguration.add(SymbolConstants.HMAC_PASSPHRASE,
				"a1TAzRnjBZRKubgwSRlpX");
	}

	/**
	 * @param pConfiguration
	 *            to use
	 */
	public static void contributeResponseCompressionAnalyzer(
			final Configuration<String> pConfiguration) {
		pConfiguration.add("application/json");
	}
}

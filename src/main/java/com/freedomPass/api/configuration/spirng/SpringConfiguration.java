package com.freedomPass.api.configuration.spirng;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.configuration.converter.AdminPassConverter;
import com.freedomPass.api.configuration.converter.GroupConverter;
import com.freedomPass.api.configuration.converter.LanguageConverter;
import com.freedomPass.api.configuration.converter.OutletOfferTypeConverter;
import com.freedomPass.api.configuration.converter.ReportConverter;
import com.freedomPass.api.configuration.converter.ReportStyleConverter;
import com.freedomPass.api.configuration.converter.RoleConverter;
import com.freedomPass.api.configuration.converter.UserCompanyInfoConverter;
import com.freedomPass.api.configuration.converter.UserConverter;
import com.freedomPass.api.configuration.converter.UserOutletInfoConverter;
import com.freedomPass.api.configuration.converter.UserOutletOfferConverter;
import com.freedomPass.api.configuration.converter.UserProfileNotificationEventConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = {"com.freedomPass.api", "com.freedomPass.project"})
public class SpringConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Added @Lazy to prevent using messageSource in UserService before its
     * being created here
     */
    @Autowired
    @Lazy
    RoleConverter roleConverter;

    @Autowired
    @Lazy
    UserConverter userConverter;

    @Autowired
    @Lazy
    UserCompanyInfoConverter userCompanyInfoConverter;
    /**
     * Added @Lazy to prevent using messageSource in UserService before its
     * being created here
     */
    @Autowired
    @Lazy
    ReportConverter reportConverter;

    /**
     * Added @Lazy to prevent using messageSource in UserService before its
     * being created here
     */
    @Autowired
    @Lazy
    GroupConverter groupConverter;

    @Autowired
    @Lazy
    UserOutletOfferConverter userOutletOfferConverter;

    @Autowired
    @Lazy
    UserOutletInfoConverter userOutletInfoConverter;

    @Autowired
    @Lazy
    OutletOfferTypeConverter uutletOfferTypeConverter;

    @Autowired
    @Lazy
    ReportStyleConverter reportStyleConverter;

    @Autowired
    @Lazy
    LanguageConverter languageConverter;

    @Autowired
    @Lazy
    UserProfileNotificationEventConverter userProfileNotificationEventConverter;

    @Autowired
    @Lazy
    AdminPassConverter adminPassConverter;

    @Autowired
    private ContextHolder context;

    /**
     * Configure ViewResolvers to deliver preferred views.
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    /**
     * Configure Converter to be used. In our example, we need a converter to
     * convert string values[Roles] to UserProfiles in newUser.jsp
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleConverter);
        registry.addConverter(adminPassConverter);
        registry.addConverter(userConverter);
        registry.addConverter(groupConverter);
        registry.addConverter(userOutletOfferConverter);
        registry.addConverter(userOutletInfoConverter);
        registry.addConverter(uutletOfferTypeConverter);
        registry.addConverter(reportStyleConverter);
        registry.addConverter(reportConverter);
        registry.addConverter(languageConverter);
        registry.addConverter(userProfileNotificationEventConverter);
        registry.addConverter(userCompanyInfoConverter);
    }

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript
     * etc...
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setCookieName("myLocaleCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

    @Bean
    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory) {

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Optional. It's only required when handling '.' in @PathVariables which
     * otherwise ignore everything after last '.' in @PathVaidables argument.
     * It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164],
     * still present in Spring 4.1.7. This is a workaround for this issue.
     *
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate4Module to support lazy objects
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Hibernate4Module hibernate5Module = new Hibernate4Module();
        hibernate5Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModule(hibernate5Module);

        messageConverter.setObjectMapper(mapper);
        messageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN}));
        return messageConverter;
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    @Bean
    public StringHttpMessageConverter stringConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.TEXT_PLAIN}));
        return converter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        return list;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
        converters.add(byteArrayHttpMessageConverter());
        converters.add(stringConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        try {
            mailSender.setHost(context.getEnvironment().getRequiredProperty("mail.host"));
            mailSender.setPort(Integer.parseInt(context.getEnvironment().getRequiredProperty("mail.port")));
            //Set gmail email id
            mailSender.setUsername(context.getEnvironment().getRequiredProperty("mail.username"));
            //Set gmail email password
            mailSender.setPassword(context.getEnvironment().getRequiredProperty("mail.password"));
            Properties prop = mailSender.getJavaMailProperties();
            prop.put("mail.transport.protocol", context.getEnvironment().getRequiredProperty("mail.transport.protocol"));
            prop.put("mail.smtp.auth", context.getEnvironment().getRequiredProperty("mail.smtp.auth"));
            prop.put("mail.smtp.starttls.enable", context.getEnvironment().getRequiredProperty("mail.smtp.starttls.enable"));
            prop.put("mail.debug", context.getEnvironment().getRequiredProperty("mail.debug"));
            prop.put("mail.mime.address.strict", context.getEnvironment().getRequiredProperty("mail.mime.address.strict"));
            return mailSender;
        } catch (Exception e) {
            return mailSender;
        }
    }

}

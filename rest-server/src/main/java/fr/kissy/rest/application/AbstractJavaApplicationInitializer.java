package fr.kissy.rest.application;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractJavaApplicationInitializer implements WebApplicationInitializer {
    /**
     * @inheritDoc
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(getJavaConfiguration());
        servletContext.addListener(new ContextLoaderListener(applicationContext));
        servletContext.addListener(new LogbackConfigListener());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(CXFServlet.class.getSimpleName(), CXFServlet.class);
        dispatcher.addMapping("/rest/*");
        dispatcher.setLoadOnStartup(1);
    }

    /**
     * Get the Java Configuration Class to Bootstrap.
     *
     * @return The Java Configuration Class to bootstrap.
     */
    protected abstract Class getJavaConfiguration();
}
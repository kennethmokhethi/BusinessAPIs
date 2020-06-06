package com.businessapi.swaggerconfig;

import io.swagger.jaxrs.config.BeanConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SwaggerConfigurationServlet extends HttpServlet {

    private static final long serialVersionUID = -3932489067441571242L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setBasePath("/api");
        beanConfig.setHost("localhost:8080");
        beanConfig.setTitle("BusinessAPIs");
        beanConfig.setVersion("2.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setResourcePackage("com.businessapi");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);

    }
}

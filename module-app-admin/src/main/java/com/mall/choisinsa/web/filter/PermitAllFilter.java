//package com.mall.choisinsa.security.filter;
//
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PermitAllFilter extends FilterSecurityInterceptor {
//    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
//    private FilterInvocationSecurityMetadataSource securityMetadataSource;
//    private boolean observeOncePerRequest = true;
//    private List<RequestMatcher> permitAllRequestMatcher = new ArrayList<>();
//
//    public PermitAllFilter(String... permitAllResources) {
//        for (String resource : permitAllResources) {
//            permitAllRequestMatcher.add(new AntPathRequestMatcher(resource));
//        }
//    }
//
//    @Override
//    protected InterceptorStatusToken beforeInvocation(Object object) {
//        HttpServletRequest request = ((FilterInvocation) object).getRequest();
//        for (RequestMatcher key : permitAllRequestMatcher) {
//            if (key.matches(request)) {
//                return null;
//            }
//        }
//
//        return super.beforeInvocation(object);
//    }
//
//    @Override
//    public void init(FilterConfig arg0) {
//
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        invoke(new FilterInvocation(request, response, chain));
//    }
//
//    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
//        return this.securityMetadataSource;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//        return this.securityMetadataSource;
//    }
//
//    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
//        this.securityMetadataSource = newSource;
//    }
//
//    @Override
//    public Class<?> getSecureObjectClass() {
//        return FilterInvocation.class;
//    }
//
//    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
//        if (isApplied(filterInvocation) && this.observeOncePerRequest) {
//            // filter already applied to this request and user wants us to observe
//            // once-per-request handling, so don't re-do security checking
//            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
//            return;
//        }
//        // first time this request being called, so perform security checking
//        if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
//            filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
//        }
//        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
//        try {
//            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
//        }
//        finally {
//            super.finallyInvocation(token);
//        }
//        super.afterInvocation(token, null);
//    }
//
//    private boolean isApplied(FilterInvocation filterInvocation) {
//        return (filterInvocation.getRequest() != null)
//                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
//    }
//
//    public boolean isObserveOncePerRequest() {
//        return this.observeOncePerRequest;
//    }
//
//    public void setObserveOncePerRequest(boolean observeOncePerRequest) {
//        this.observeOncePerRequest = observeOncePerRequest;
//    }
//}

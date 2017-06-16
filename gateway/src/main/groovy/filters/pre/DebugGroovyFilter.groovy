package filters.pre

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DebugGroovyFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(DebugGroovyFilter.class)

    @Override
    String filterType() {
        return "pre"
    }

    @Override
    int filterOrder() {
        return 1
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        logger.debug("Executing DebugGroovyFilter.")

        RequestContext context = RequestContext.getCurrentContext()
        context.setDebugRequest(true)
        context.setDebugRouting(true)
        return null
    }
}



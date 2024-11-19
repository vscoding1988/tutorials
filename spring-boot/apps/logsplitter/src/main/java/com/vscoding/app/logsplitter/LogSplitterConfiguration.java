package com.vscoding.app.logsplitter;

import com.vscoding.app.logsplitter.bean.LogSplitterPattern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LogSplitterConfiguration {

  @Bean
  public List<LogSplitterPattern> pattern() {
    return Arrays.asList(
            new LogSplitterPattern(Arrays.asList("GET", "POST", "HEAD", "OPTIONS"), "access", LogSplitterPattern.Type.DISALLOW_FURTHER_MAP),
            new LogSplitterPattern(Arrays.asList("No placement named"), "placement-error"),
            new LogSplitterPattern(Arrays.asList("Cannot build link for content", "has no navigation context"), "link-error-clean"),
            new LogSplitterPattern(Arrays.asList("getVariantsByLocale"), "duplicate-variant"),
            new LogSplitterPattern(Arrays.asList("SuggesterSearcher"), "search"),
            new LogSplitterPattern(Arrays.asList("has no navigation context"), "missing-context"),
            new LogSplitterPattern(Arrays.asList("Sitemap is", "SitemapTriggerImpl", "SitemapIndexRenderer", "SitemapGenerationJob"), "sitemap"),
            new LogSplitterPattern(Arrays.asList("ExceptionHandlingViewDecorator", "NoViewFound", "ViewInclusion", "FTL stack trace", "Tip: If", "- Failed at", "com.coremedia.objectserver.view.ViewUtils", "Adjust your templates or "), "freemarker-error"),
            new LogSplitterPattern(Arrays.asList("MailLimiterService"), "mail"),
            new LogSplitterPattern(Arrays.asList("CatalogId"), "shop"),
            new LogSplitterPattern(Arrays.asList("proxy_http:error", "proxy:error", "rewrite:error"), "proxy-error"),
            new LogSplitterPattern(Arrays.asList("+ rclone copy", "+ true", "+ sleep 10", "/data/remco", "remco_linux"), "admin-rewrites-crontask"),
            new LogSplitterPattern(Arrays.asList("TransformedBlobCache", "+ rclone copy", "+ true", "+ sleep 10", "/data/remco", "remco_linux"), "junk"),
            new LogSplitterPattern(Arrays.asList("+ rclone copy", "S3 bucket sitemaps:", "Transferred:", "Errors:", "Checks:", "Elapsed time:", "+ sleep 300", "minio:sitemaps/", "SartoriusCN/", "SartoriusWW/", "SartoriusKR/", "SartoriusBrandPlatform/", "SartoriusRU/", "INFO  : "), "admin-sitemap-crontask"),
            new LogSplitterPattern(Arrays.asList("No link scheme found", "Cannot not create link for", "Cannot build link for content", "unable to build link for bean", "IllegalArgumentException: No link scheme found"), "link-error"),

            // Fallback
            new LogSplitterPattern(Arrays.asList("Exception:"), "java-exceptions",LogSplitterPattern.Type.SINGLE_MAP)
    );
  }
}

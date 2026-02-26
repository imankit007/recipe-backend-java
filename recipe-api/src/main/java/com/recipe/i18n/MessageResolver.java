package com.recipe.i18n;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageResolver {

    private final Map<Locale, Properties> cache = new ConcurrentHashMap<>();

    public String resolve(String code, Locale locale, Map<String, String> params) {
        // normalize locale to language tag only
        Locale keyLocale = Locale.forLanguageTag(locale.getLanguage());
        Properties props = cache.computeIfAbsent(keyLocale, this::loadPropertiesForLocale);
        String template = props.getProperty(code);
        if (template == null) {
            // fallback to English
            Properties en = cache.computeIfAbsent(Locale.forLanguageTag("en"), this::loadPropertiesForLocale);
            template = en.getProperty(code);
            if (template == null) {
                // fallback: show code itself
                return code;
            }
        }
        if (params == null || params.isEmpty()) {
            return template;
        }
        // Order params deterministically by sorted key names
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        Object[] ordered = keys.stream().map(params::get).toArray();
        return MessageFormat.format(template, ordered);
    }

    private Properties loadPropertiesForLocale(Locale locale) {
        Properties p = new Properties();
        String base = "messages_" + locale.getLanguage() + ".properties";
        try (var is = new ClassPathResource(base).getInputStream()) {
            p.load(new java.io.InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            // no file -> empty properties
        }
        return p;
    }
}

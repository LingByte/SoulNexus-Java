package com.lingecho.common.core.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslatorUtils {

    private static final Logger logger = LoggerFactory.getLogger(TranslatorUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final HttpClient client;
    private final String baseUrl = "https://api.mymemory.translated.net/get";
    private final String email = "support@lingecho.com";
    private final String userAgent = "LingFramework/1.0";

    public TranslatorUtils() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MyMemoryResponse {
        @JsonProperty("responseData")
        public ResponseData responseData;

        @JsonProperty("quotaFinished")
        public boolean quotaFinished;

        @JsonProperty("mtLangSupported")
        public boolean mtLangSupported;

        @JsonProperty("responseDetails")
        public String responseDetails;

        @JsonProperty("responseStatus")
        public int responseStatus;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ResponseData {
            @JsonProperty("translatedText")
            public String translatedText;

            @JsonProperty("match")
            public double match;
        }
    }

    public String translate(String text, String from, String to) throws Exception {
        if (text.isBlank()) {
            return "";
        }

        from = normalizeLangCode(from);
        to = normalizeLangCode(to);

        if (from.equals(to)) {
            return text;
        }

        String langPair = URLEncoder.encode(from + "|" + to, StandardCharsets.UTF_8);
        String q = URLEncoder.encode(text, StandardCharsets.UTF_8);

        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?q=").append(q);
        url.append("&langpair=").append(langPair);
        if (email != null && !email.isBlank()) {
            url.append("&de=").append(URLEncoder.encode(email, StandardCharsets.UTF_8));
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .header("User-Agent", userAgent)
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("API returned status: " + response.statusCode() + ", body: " + response.body());
        }

        MyMemoryResponse resp = objectMapper.readValue(response.body(), MyMemoryResponse.class);

        if (resp.responseStatus != 200) {
            throw new Exception("API error: " + resp.responseDetails + " (status:" + resp.responseStatus + ")");
        }

        if (resp.quotaFinished) {
            logger.warn("MyMemory translation quota finished");
            throw new Exception("translation quota finished");
        }

        String translated = resp.responseData.translatedText;
        if (translated == null || translated.isBlank()) {
            return text;
        }

        logger.debug("translation completed from {} to {}, match: {}", from, to, resp.responseData.match);
        return translated;
    }

    public List<String> translateBatch(List<String> texts, String from, String to) throws Exception {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            String t = texts.get(i);
            String res = translate(t, from, to);
            results.add(res);
            Thread.sleep(100);
        }
        return results;
    }

    public static String normalizeLangCode(String lang) {
        if (lang == null) return "en";
        lang = lang.trim().toLowerCase();

        Map<String, String> map = new HashMap<>();
        map.put("zh", "zh-CN");
        map.put("zh-cn", "zh-CN");
        map.put("zh-tw", "zh-TW");
        map.put("zh-hk", "zh-TW");
        map.put("en", "en");
        map.put("en-us", "en");
        map.put("en-gb", "en");
        map.put("es", "es");
        map.put("fr", "fr");
        map.put("de", "de");
        map.put("it", "it");
        map.put("pt", "pt");
        map.put("ru", "ru");
        map.put("ja", "ja");
        map.put("ko", "ko");
        map.put("ar", "ar");
        map.put("hi", "hi");
        map.put("th", "th");
        map.put("vi", "vi");
        map.put("id", "id");
        map.put("tr", "tr");
        map.put("pl", "pl");
        map.put("nl", "nl");
        map.put("sv", "sv");
        map.put("da", "da");
        map.put("fi", "fi");
        map.put("no", "no");
        map.put("cs", "cs");
        map.put("hu", "hu");
        map.put("ro", "ro");
        map.put("el", "el");
        map.put("he", "he");
        map.put("uk", "uk");
        map.put("bg", "bg");
        map.put("hr", "hr");
        map.put("sk", "sk");
        map.put("sl", "sl");
        map.put("et", "et");
        map.put("lv", "lv");
        map.put("lt", "lt");
        map.put("mt", "mt");
        map.put("ga", "ga");
        map.put("cy", "cy");

        if (map.containsKey(lang)) {
            return map.get(lang);
        }

        if (lang.contains("-")) {
            String[] parts = lang.split("-", 2);
            if (map.containsKey(parts[0])) {
                return map.get(parts[0]);
            }
        }

        return lang;
    }

    public static List<String> chunkRunes(String s, int maxRunes) {
        if (maxRunes <= 0) maxRunes = 400;
        if (s.isBlank()) return new ArrayList<>();

        List<String> chunks = new ArrayList<>();
        int[] codePoints = s.codePoints().toArray();
        int len = codePoints.length;

        for (int i = 0; i < len; i += maxRunes) {
            int end = Math.min(i + maxRunes, len);
            String chunk = new String(codePoints, i, end - i);
            chunks.add(chunk);
        }
        return chunks;
    }

    public static String translateLong(TranslatorUtils translator,
                                       String text,
                                       String from,
                                       String to,
                                       int maxRunes,
                                       long pauseMs) throws Exception {
        if (text.isBlank()) return "";

        long len = text.codePoints().count();
        if (len <= maxRunes) {
            return translator.translate(text, from, to);
        }

        List<String> chunks = chunkRunes(text, maxRunes);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chunks.size(); i++) {
            if (i > 0 && pauseMs > 0) {
                Thread.sleep(pauseMs);
            }
            String part = translator.translate(chunks.get(i), from, to);
            sb.append(part);
        }
        return sb.toString();
    }

    public static List<String> getSupportedLanguages() {
        return List.of(
                "en", "zh-CN", "zh-TW", "es", "fr", "de", "it", "pt", "ru",
                "ja", "ko", "ar", "hi", "th", "vi", "id", "tr", "pl", "nl",
                "sv", "da", "fi", "no", "cs", "hu", "ro", "el", "he", "uk",
                "bg", "hr", "sk", "sl", "et", "lv", "lt", "mt", "ga", "cy"
        );
    }

    public static void main(String[] args) throws Exception {
        TranslatorUtils translator = new TranslatorUtils();
        String result = translator.translate("Hello world", "en", "zh-CN");
        System.out.println(result);
    }
}
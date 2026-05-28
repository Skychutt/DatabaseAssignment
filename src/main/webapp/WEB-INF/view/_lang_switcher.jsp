<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>document.documentElement.lang = "en";</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/lang-switcher.css">
<script>
    window.__forceSetLang = function (lang) {
        try { sessionStorage.setItem("app_lang", lang); } catch (e) {}
        document.documentElement.lang = lang === "zh" ? "zh" : "en";
        if (window.I18N && typeof window.I18N.applyLanguage === "function") {
            try {
                window.I18N.applyLanguage(lang);
                return false;
            } catch (e0) { /* ignore */ }
        }
        window.location.reload();
        return false;
    };
</script>
<div id="lang-switcher" aria-label="Language switch">
    <div class="lang-switcher-inner">
        <button type="button" class="lang-btn" id="lang-btn-zh" title="Chinese" style="pointer-events:auto;" onclick="return window.__forceSetLang('zh');">中文</button>
        <button type="button" class="lang-btn active" id="lang-btn-en" title="English" style="pointer-events:auto;" onclick="return window.__forceSetLang('en');">English</button>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/i18n.js?v=20260528e"></script>

/**
 * 为 jQuery AJAX POST 自动附加会话 CSRF 令牌（与 AuthFilter / CsrfUtil 配合）。
 */
(function () {
    var meta = document.querySelector('meta[name="csrf-token"]');
    if (!meta) {
        return;
    }
    var token = meta.getAttribute('content');
    if (!token) {
        return;
    }

    function attachToken(options) {
        var method = (options.type || options.method || 'GET').toUpperCase();
        if (method !== 'POST') {
            return;
        }
        if (options.data == null) {
            options.data = {_csrf: token};
            return;
        }
        if (typeof options.data === 'string') {
            options.data += (options.data.length ? '&' : '') + '_csrf=' + encodeURIComponent(token);
            return;
        }
        if (options.data instanceof FormData) {
            options.data.append('_csrf', token);
            return;
        }
        options.data._csrf = token;
    }

    function install() {
        if (!window.jQuery) {
            setTimeout(install, 30);
            return;
        }
        jQuery.ajaxPrefilter(function (options) {
            attachToken(options);
        });
    }

    install();
})();

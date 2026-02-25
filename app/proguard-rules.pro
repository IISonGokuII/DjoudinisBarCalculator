# No special ProGuard rules needed for WebView app
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

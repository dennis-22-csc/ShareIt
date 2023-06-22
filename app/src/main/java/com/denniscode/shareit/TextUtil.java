package com.denniscode.shareit;

import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    private static final String URL_REGEX_PATTERN = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";

    public static void slideText(TextView textDashboardView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(textDashboardView, "translationX", -500f, 0f);
        animator.setDuration(1000); // Adjust the duration as needed
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static boolean isValidURL(String text) {
        Pattern pattern = Pattern.compile(URL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}

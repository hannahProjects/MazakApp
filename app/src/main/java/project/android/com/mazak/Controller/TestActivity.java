package project.android.com.mazak.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import project.android.com.mazak.Model.Entities.BackgroundTask;
import project.android.com.mazak.Model.Entities.Delegate;
import project.android.com.mazak.Model.Entities.NameValuePair;
import project.android.com.mazak.Model.Web.ConnectionData;
import project.android.com.mazak.Model.Web.MazakConnection;
import project.android.com.mazak.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new BackgroundTask(new Delegate() {
            @Override
            public void function(Object obj) {
                webfunc();
            }
        }).start();
    }

    private void webfunc() {
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("__EVENTTARGET",""));
        params.add(new NameValuePair("__EVENTARGUMENT",""));
        params.add(new NameValuePair("__VIEWSTATE","/wEPDwUJNDAyMTI1OTYwD2QWAmYPZBYCZg9kFgICAw9kFgICAw9kFgICBQ9kFgICEw8PFgYeBkhlaWdodBsAAAAAAEB/QAEAAAAeBVdpZHRoGwAAAAAAQH9AAQAAAB4EXyFTQgKAAxYCHgVzdHlsZQUZaGVpZ2h0OjUwMHB4O3dpZHRoOjUwMHB4OxYEZg8UKwACFCsAAhYCHgFDBQNydGxkZBYCZg9kFgwFIVN0dWRlbnRDb3Vyc2VQYXJ0R3JhZGVVc2VyQ29udHJvbA9kFgoCAQ88KwAKAQAPFgQeC18hRGF0YUJvdW5kZx4LXyFJdGVtQ291bnQCAWQWAmYPZBYGZg8PFgIeB1Zpc2libGVoZGQCAQ9kFgJmD2QWAmYPFQQOMTIwMDAwLjAxLjU3NzVC16fXldeo16Eg16fXk9edINeR157Xqtee15jXmden15Qg15zXlNeg15PXodeUINeV15jXm9eg15XXnNeV15LXmdeUCdeq16nXoiLXlAjXkNec15XXnGQCAg8PFgIfB2hkZAIDDxQrAAIPFgQfBWcfBgIBZGQWAmYPZBYCAgEPZBYCZg9kFgJmD2QWAgIBD2QWAgIBD2QWAmYPZBYCZg8VCxHXnteR15fXnyDXodeV16TXmQI1NQYxMDAuMDAAAjk1AAAAAAAAZAIFDxQrAAIPFgQfBWcfBgIBZGQWAmYPZBYCAgEPZBYCZg9kFgJmD2QWAgIBD2QWAgIBD2QWAmYPZBYGZg8VAxHXnteR15fXnyDXodeV16TXmQvXnteV16LXkyDXkABkAgMPDxYEHgRUZXh0BQlDb2xvci5wZGYeD0NvbW1hbmRBcmd1bWVudAUGMjU1MjUwZGQCBA8VAQ4wMi8xMS8xNCAxMDo0NmQCBw8UKwACDxYEHwVnHwZmZGRkAgkPFCsAAg8WBB8FZx8GAgRkZBYCZg9kFgICAQ9kFgJmD2QWAmYPZBYCAgEPZBYIAgEPZBYCZg9kFgZmDxUDEdee15HXl9efINeh15XXpNeZC9ee15XXoteTINeQOteq15DXqNeZ15og15DXl9eo15XXnyDXnNei16jXoteV16gg16LXkdeoIC0gMDkvMTEvMTQgMjM6NTlkAgEPDxYEHgtOYXZpZ2F0ZVVybAU6fi9TdHVkZW50L0FwcGVhbC5hc3B4P1BhcnRHcmFkZUlEPTE0MTIxOTImVGVzdFRpbWVUeXBlSUQ9MR8HaGRkAgMPDxYEHwoFOn4vU3R1ZGVudC9BcHBlYWwuYXNweD9QYXJ0R3JhZGVJRD0xNDEyMTkyJlRlc3RUaW1lVHlwZUlEPTEfB2hkZAICD2QWAmYPZBYGZg8VAxHXnteR15fXnyDXodeV16TXmQvXnteV16LXkyDXkTTXnNeQINeg15nXqtefINec16LXqNei16gg16LXnCDXpteZ15XXnyDXnteh15XXkiDXlteUZAIBDw8WBB8KBTp+L1N0dWRlbnQvQXBwZWFsLmFzcHg/UGFydEdyYWRlSUQ9MTQxMjE5MiZUZXN0VGltZVR5cGVJRD0yHwdoZGQCAw8PFgQfCgU6fi9TdHVkZW50L0FwcGVhbC5hc3B4P1BhcnRHcmFkZUlEPTE0MTIxOTImVGVzdFRpbWVUeXBlSUQ9Mh8HaGRkAgMPZBYCZg9kFgZmDxUDEdee15HXl9efINeh15XXpNeZE9ee15XXoteTINee15nXldeX15M015zXkCDXoNeZ16rXnyDXnNei16jXoteoINei15wg16bXmdeV158g157XodeV15Ig15bXlGQCAQ8PFgQfCgU6fi9TdHVkZW50L0FwcGVhbC5hc3B4P1BhcnRHcmFkZUlEPTE0MTIxOTImVGVzdFRpbWVUeXBlSUQ9Mx8HaGRkAgMPDxYEHwoFOn4vU3R1ZGVudC9BcHBlYWwuYXNweD9QYXJ0R3JhZGVJRD0xNDEyMTkyJlRlc3RUaW1lVHlwZUlEPTMfB2hkZAIED2QWAmYPZBYGZg8VAxHXnteR15fXnyDXodeV16TXmQvXnteV16LXkyDXkjTXnNeQINeg15nXqtefINec16LXqNei16gg16LXnCDXpteZ15XXnyDXnteh15XXkiDXlteUZAIBDw8WBB8KBTp+L1N0dWRlbnQvQXBwZWFsLmFzcHg/UGFydEdyYWRlSUQ9MTQxMjE5MiZUZXN0VGltZVR5cGVJRD00HwdoZGQCAw8PFgQfCgU6fi9TdHVkZW50L0FwcGVhbC5hc3B4P1BhcnRHcmFkZUlEPTE0MTIxOTImVGVzdFRpbWVUeXBlSUQ9NB8HaGRkBRRQYXJ0R3JhZGVzRGF0YVNvdXJjZQ8PZA8QFgJmAgEWAhYEHg5QYXJhbWV0ZXJWYWx1ZWQeDERlZmF1bHRWYWx1ZQUFMjIzMzMWBB8LZB8MBQUyMTI4MBYCAgMCA2RkBRBDb3Vyc2VEYXRhU291cmNlDw9kDxAWAWYWARYEHwtkHwwFBTIxMjgwFgECA2RkBRdUZXN0Tm90ZWJvb2tzRGF0YVNvdXJjZQ8PZA8QFgJmAgEWAhYEHwtkHwwFBTIyMzMzFgQfC2QfDAUFMjEyODAWAgIDAgNkZAUqQ29tcHV0ZXJpemVkUXVlc3Rpb25uYWlyZUFuc3dlcnNEYXRhU291cmNlDw9kDxAWAmYCARYCFgQfC2QfDAUFMjIzMzMWBB8LZB8MBQUyMTI4MBYCAgMCA2RkBRFBcHBlYWxzRGF0YVNvdXJjZQ8PZA8QFgJmAgEWAhYEHwtkHwwFBTIyMzMzFgQfC2QfDAUFMjEyODAWAgIDAgNkZAIBDxQrAAQUKwACZGQWAh4BYQUITWluaW1pemUWBB8NBQhNYXhpbWl6ZR4BdmcWAh8NBQVDbG9zZWQYCAU9Y3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcw8UKwAOZGRkZGRkZDwrADYAAjZkZGRmAv////8PZAV/Y3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGRsZ0NvdXJzZVBhcnRHcmFkZXMkdG1wbCRTdHVkZW50Q291cnNlUGFydEdyYWRlVXNlckNvbnRyb2wkZ3JkVGVzdE5vdGVib29rcw8UKwAOZGRkZGRkZBQrAAFkAgFkZGRmAv////8PZAUpY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRMb2dpblZpZXcPD2QCAWQFkgFjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZGxnQ291cnNlUGFydEdyYWRlcyR0bXBsJFN0dWRlbnRDb3Vyc2VQYXJ0R3JhZGVVc2VyQ29udHJvbCRncmRDb21wdXRlcml6ZWRRdWVzdGlvbm5haXJlQW5zd2Vycw88KwAOAwhmDGYNAv////8PZAV8Y3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGRsZ0NvdXJzZVBhcnRHcmFkZXMkdG1wbCRTdHVkZW50Q291cnNlUGFydEdyYWRlVXNlckNvbnRyb2wkZ3JkUGFydEdyYWRlcw8UKwAOZGRkZGRkZBQrAAFkAgFkZGRmAv////8PZAV5Y3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGRsZ0NvdXJzZVBhcnRHcmFkZXMkdG1wbCRTdHVkZW50Q291cnNlUGFydEdyYWRlVXNlckNvbnRyb2wkZ3JkQXBwZWFscw8UKwAOZGRkZGRkZDwrAAQAAgRkZGRmAv////8PZAUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFjwFOGN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkTG9naW5TdGF0dXNDb250cm9sJGN0bDAxBThjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJExvZ2luU3RhdHVzQ29udHJvbCRjdGwwMwVJY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJHVjQ29tbWFuZFNob3ckYnRuU2hvdwVWY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJENvdXJzZUNvbG9yUmVhZGVyVXNlckNvbnRyb2wkY3RsMDAFUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDAkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDEkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDIkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDMkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDQkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDUkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDYkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDckYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDgkYnRuSW5mb3JtYXRpb24FUmN0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDkkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDEwJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwxMSRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMTIkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDEzJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwxNCRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMTUkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDE2JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwxNyRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMTgkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDE5JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwyMCRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMjEkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDIyJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwyMyRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMjQkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDI1JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwyNiRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMjckYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDI4JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwyOSRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMzAkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDMxJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwzMiRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMzMkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDM0JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwzNSRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMzYkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDM3JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmwzOCRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsMzkkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDQwJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmw0MSRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsNDIkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDQzJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmw0NCRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsNDUkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDQ2JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmw0NyRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsNDgkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDQ5JGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmw1MCRidG5JbmZvcm1hdGlvbgVTY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGdyZEdyYWRlcyRjdHJsNTEkYnRuSW5mb3JtYXRpb24FU2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRncmRHcmFkZXMkY3RybDUyJGJ0bkluZm9ybWF0aW9uBVNjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZ3JkR3JhZGVzJGN0cmw1MyRidG5JbmZvcm1hdGlvbgVXY3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJENvdXJzZUNvbG9yUmVhZGVyVXNlckNvbnRyb2wyJGN0bDAwBUdjdGwwMCRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJENvbnRlbnRQbGFjZUhvbGRlcjEkZGxnQ291cnNlUGFydEdyYWRlcwV4Y3RsMDAkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRDb250ZW50UGxhY2VIb2xkZXIxJGRsZ0NvdXJzZVBhcnRHcmFkZXMkdG1wbCRTdHVkZW50Q291cnNlUGFydEdyYWRlVXNlckNvbnRyb2wkZnJtQ291cnNlDxQrAAdkZGRkZBYAAgFkIkk8OQslmEvr0vv2lh6vnEcc308="));
        params.add(new NameValuePair("__VIEWSTATEGENERATOR","2D83CBBC"));
        params.add(new NameValuePair("__SCROLLPOSITIONX","0"));
        params.add(new NameValuePair("__SCROLLPOSITIONY","0"));
        params.add(new NameValuePair("__EVENTVALIDATION","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"));
        params.add(new NameValuePair("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder150$cmbAcademicYears",""));
        params.add(new NameValuePair("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder150$cmbSemsters",""));
        params.add(new NameValuePair("tl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$grdGrades$ctrl0$btnInformation.x","13"));
        params.add(new NameValuePair("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$grdGrades$ctrl0$btnInformation.y","9"));
        params.add(new NameValuePair("ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_dlgCoursePartGrades_clientState","|0|3,0,191px,213px,500px,500px,0||[[[[null,0,\"42px\",\"557px\",\"500px\",\"500px\",1,null,1,1,null,3]],[[[[[null,null,null]],[[[[[]],[],[]],[{},[]],null],[[[[]],[],[]],[{},[]],null],[[[[]],[],[]],[{},[]],null]],[]],[{},[]],null],[[[[null,null,null,null]],[],[]],[{},[]],null]],[]],[{\"2\":[2,\"191px\"],\"3\":[3,\"213px\"],\"4\":[1,3],\"5\":[11,0]},[]],\"3,0,191px,213px,500px,500px,0\"]"));
        params.add(new NameValuePair("ctl00$ctl00$_IG_CSS_LINKS_","~/App_Themes/DefaultTheme/main.css?t=636361796912346512|~/App_Themes/DefaultTheme/StyleSheet.css?t=636361796912346512|../ig_res/Default/ig_dialogwindow.css|../ig_res/Default/ig_shared.css"));
        try {
            MazakConnection connection = new MazakConnection("yyigal","*******");
            String query = connection.getQuery(params);
            connection.connect(ConnectionData.GradesURL);
            String response = connection.sendPost(ConnectionData.GradesURL,query);
            int x = 5;
            x++;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

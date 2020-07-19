package com.cml.drrrchatapp;

import android.webkit.WebView;
import android.widget.EditText;

import java.util.ArrayList;

public class JavaScriptManager {
    WebView webView;
    EditText jsField;

    JavaScriptManager(WebView webView) {
        this.webView = webView;
    }

    public void setJSField(EditText jsField) {
        this.jsField = jsField;
    }

    public void runJSCommand(String codeInJS) {
        if (codeInJS.length() > 3 && codeInJS.substring(0, 3).equals("-an")) {
            webView.evaluateJavascript(codeInJS.substring(3), null);
        } else {
            webView.evaluateJavascript(codeInJS.replace("\n", " ").replace("\r", " "), null);
        }

    }

    void loadAll() {
        ArrayList<String> scriptList = new JSLibrary().getScriptList();
        String toSet = "";
        for (String i : scriptList) {
            runJSCommand(i);
            if (jsField != null) {
                toSet += (i.split("[(]")[0] + "\n");
            }
        }
        jsField.setText(toSet);

    }


}

class JSLibrary {
    String meAdder = "function autoMe() {$('#message').submit(function() {\n" +
            "     document.getElementsByName(\"message\")[0].value =\"/me \";\n" +
            "    return true; \n" +
            "}); void(0);} ";
    String loadPage = "function goto(x){\n" +
            "window.location.href = x;\n" +
            "}";
    private ArrayList<String> scriptList = new ArrayList<>();

    JSLibrary() {
        scriptList.add(meAdder);
        scriptList.add(loadPage);


    }

    public ArrayList<String> getScriptList() {
        return scriptList;
    }

}

//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
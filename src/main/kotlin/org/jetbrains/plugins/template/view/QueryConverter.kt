package org.jetbrains.plugins.template.view;

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import org.jetbrains.plugins.template.MyBundle
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.*;
import kotlin.streams.toList

class QueryConverter(project: Project, toolWindow: ToolWindow?): JPanel() {
    //
    val toolWindow = toolWindow
    val project = project


    var view: JPanel? = null;
    var convertBtn: JButton? = null;
    var queryText: JTextArea? = null;
    var logText: JTextArea? = null;
    var resText: JTextArea? = null;
    var clearBtn: JButton? = null;
    var closeBtn: JButton? = null;


    init {
        add(view);

        convertBtn?.addActionListener{
            val query = queryText?.text;
            val log = logText?.text;
            if (query!=null && log!=null) {
                var resultQuery = genQuery(query, log);
                println(genQuery(query, log))
                resText?.text = resultQuery;
                copyToClipboard(resultQuery);
            }
        }

        clearBtn?.addActionListener{
            queryText?.text = null;
            logText?.text = null;
            resText?.text = null;
        }

        closeBtn?.addActionListener{
            toolWindow?.hide(null);
            // test
        }

    }

    fun genQuery (query: String, log: String):String {
        var extractedQuery = extractQuery(query);
        var extractedParameters = extractParameters(log);
        for (parameter in extractedParameters) {
            extractedQuery = extractedQuery.replaceFirst("?", parameter.toString());
        };
        return extractedQuery;
    }

    fun extractQuery (query: String): String {
        return query.substringAfter("org.hibernate.SQL").trim().substringAfter(":");
    }

    fun extractParameters (log: String): List<Any> {
        return log.split("org.hibernate.orm.jdbc.bind").stream().map { it -> it.substringBefore("\n") }.filter { it -> it.contains("binding parameter") }.map { it -> it.substringAfter("[").substringBefore("]") }.toList().reversed();
    }

    fun copyToClipboard(text: String) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val selection = StringSelection(text)
        clipboard.setContents(selection, null)
    }

}


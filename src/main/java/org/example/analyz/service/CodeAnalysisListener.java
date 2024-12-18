package org.example.analyz.service;

import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.example.analyz.grammar.JavaParserBaseListener;
import org.example.analyz.grammar.JavaParser;

import java.util.ArrayList;
import java.util.List;

public class CodeAnalysisListener extends JavaParserBaseListener {

    private final List<String> issues = new ArrayList<>();

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        // Проверяем на пустые методы
        if (ctx.methodBody() != null && ctx.methodBody().block() != null
                && ctx.methodBody().block().blockStatement().isEmpty()) {
            String methodName = ctx.identifier().getText(); // Получаем имя метода
            issues.add("Empty method detected: " + methodName);
        }
    }

    @Override
    public void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
        // Проверяем переменные без инициализации
        if (ctx.variableInitializer() == null) {
            issues.add("Uninitialized variable detected: " + ctx.variableDeclaratorId().getText());
        }
    }

    public String getReport() {
        if (issues.isEmpty()) {
            return "No issues found. Your code is clean!";
        }
        return String.join("\n", issues);
    }
}

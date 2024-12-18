package org.example.analyz.service;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.example.analyz.grammar.JavaLexer;
import org.example.analyz.grammar.JavaParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class CodeAnalysisService {

    public String analyzeCode(MultipartFile file) throws IOException {
        // Проверяем формат файла
        if (!file.getOriginalFilename().endsWith(".java")) {
            throw new IOException("Only .java files are allowed.");
        }

        // Парсим загруженный файл
        InputStream inputStream = file.getInputStream();
        CharStream charStream = CharStreams.fromStream(inputStream);

        JavaLexer lexer = new JavaLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);

        ParseTree tree = parser.compilationUnit(); // Начинаем с корня Java-грамматики

        // Запускаем анализ AST (собственные правила)
        CodeAnalysisListener listener = new CodeAnalysisListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);

        return listener.getReport();
    }
}

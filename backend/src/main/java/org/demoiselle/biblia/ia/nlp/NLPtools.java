/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.biblia.ia.nlp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 *
 * @author 70744416353
 */
// baixar arquivo em http://opennlp.sourceforge.net/models-1.5/
// baixar arquivo em https://github.com/kinow/opennlp-dmclatam/blob/master/src/main/resources/models/pt-ner-person.bin
public class NLPtools {

    public Map<String, String> persons(String texto) {
        Map<String, String> resultado = new HashMap<>();
        try {

            String tokens[] = tokenizer(texto).toArray(new String[tokenizer(texto).size()]);
            InputStream modelPerson = new FileInputStream("/opt/demoiselle/pt-ner-person.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(modelPerson);
            NameFinderME finder = new NameFinderME(model);
            Span[] nameSpans = finder.find(tokens);

            for (Span nameSpan : nameSpans) {
                String nome = "";
                for (int index = nameSpan.getStart(); index < nameSpan.getEnd(); index++) {
                    nome = tokens[index] + " " + nome;
                }
                resultado.put(nome, nameSpan.toString());
            }
            modelPerson.close();
            return resultado;
        } catch (IOException ex) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public Map<String, String> category(String texto) {
        Map<String, String> resultado = new HashMap<>();
        try {

            String tokens[] = tokenizer(texto).toArray(new String[tokenizer(texto).size()]);
            InputStream modelClassifier = new FileInputStream("/opt/demoiselle/pt-classifier-maxent.bin");
            DoccatModel model = new DoccatModel(modelClassifier);
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            double[] aProbs = doccat.categorize(tokens);

            for (int i = 0; i < doccat.getNumberOfCategories(); i++) {
                resultado.put(doccat.getCategory(i), "" + aProbs[i]);
            }

            modelClassifier.close();
            return resultado;
        } catch (IOException ex) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public HashSet<String> tokenizer(String texto) {
        HashSet<String> resultado = new HashSet<>();

        try {
            InputStream modelToken = new FileInputStream("/opt/demoiselle/pt-token.bin");
            TokenizerModel model = new TokenizerModel(modelToken);
            TokenizerME tokenizer = new TokenizerME(model);
            String token[] = tokenizer.tokenize(texto);
            resultado.addAll(Arrays.asList(token));
            modelToken.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;

    }

    public HashSet<String> sentenceDetect(String text) {
        HashSet<String> resultado = new HashSet<>();
        try {

            InputStream modelSentence = new FileInputStream("/opt/demoiselle/pt-sent.bin");
            SentenceModel model = new SentenceModel(modelSentence);
            SentenceDetectorME sdetector = new SentenceDetectorME(model);
            String sentences[] = sdetector.sentDetect(text);
            resultado.addAll(Arrays.asList(sentences));
            modelSentence.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public Map<String, String> POSTaggerMaxent(String texto) {
        Map<String, String> resultado = new HashMap<>();

        try {

            InputStream modelPOSmaxent = new FileInputStream("/opt/demoiselle/pt-pos-maxent.bin");
            String tokens[] = tokenizer(texto).toArray(new String[tokenizer(texto).size()]);
            POSModel posModel = new POSModel(modelPOSmaxent);
            POSTaggerME posTagger = new POSTaggerME(posModel);
            String tags[] = posTagger.tag(tokens);
            modelPOSmaxent.close();

            for (int i = 0; i < tokens.length; i++) {
                resultado.put(tokens[i], tags[i]);
            }

        } catch (IOException e) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, e);
        }

        return resultado;
    }

    public Map<String, String> POSTaggerPerceptron(String texto) {
        Map<String, String> resultado = new HashMap<>();

        try {
            String tokens[] = tokenizer(texto).toArray(new String[tokenizer(texto).size()]);
            InputStream modelPOSperceptron = new FileInputStream("/opt/demoiselle/pt-pos-perceptron.bin");
            POSModel posModel = new POSModel(modelPOSperceptron);
            POSTaggerME posTagger = new POSTaggerME(posModel);
            String tags[] = posTagger.tag(tokens);
            modelPOSperceptron.close();
            for (int i = 0; i < tokens.length; i++) {
                resultado.put(tokens[i], tags[i]);
            }

        } catch (IOException e) {
            Logger.getLogger(NLPtools.class.getName()).log(Level.SEVERE, null, e);
        }

        return resultado;
    }

}

package com.dev.springchatbot.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.comprehend.model.ComprehendException;
import software.amazon.awssdk.services.comprehend.model.DetectDominantLanguageRequest;
import software.amazon.awssdk.services.comprehend.model.DetectDominantLanguageResponse;
import software.amazon.awssdk.services.comprehend.model.DominantLanguage;
import software.amazon.awssdk.services.lexruntime.LexRuntimeClient;
import software.amazon.awssdk.services.lexruntime.model.LexRuntimeException;
import software.amazon.awssdk.services.lexruntime.model.PostTextRequest;
import software.amazon.awssdk.services.lexruntime.model.PostTextResponse;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateException;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class BotService {
    public String getText(String text) {

        Region region = Region.US_EAST_1;
        LexRuntimeClient lexRuntimeClient = LexRuntimeClient.builder()
                .region(region)
                .build();

        String englishMessage = "";

        try{
            String detectedLanguageCode = DetectLanguage(text);

            if(detectedLanguageCode.compareTo("en") != 0){
                englishMessage = translateToEnglish(detectedLanguageCode, text);
            }else {
                englishMessage = text;
            }

            String userId = "chatBot";

            Map<String, String> sessionAttributes = new HashMap<>();
            PostTextRequest postTextRequest = PostTextRequest.builder()
                    .botName("Order")
                    .botAlias("placeOrder")
                    .inputText(englishMessage)
                    .userId(userId)
                    .sessionAttributes(sessionAttributes)
                    .build();

            PostTextResponse postTextResponse = lexRuntimeClient.postText(postTextRequest);
            String message = postTextResponse.message();

            String resultText = "";
            if(detectedLanguageCode.compareTo("en") != 0){
                resultText = translateTextFromEnglish(detectedLanguageCode, message);
            }else {
                resultText = message;
            }
            return resultText;
        }catch (LexRuntimeException exception){
            System.err.println(exception.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

    private String translateTextFromEnglish(String detectedLanguageCode, String message) {
        Region region = Region.US_EAST_1;
        TranslateClient translateClient = TranslateClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(region)
                .build();

        try {
            TranslateTextRequest translateTextRequest = TranslateTextRequest.builder()
                    .sourceLanguageCode("en")
                    .targetLanguageCode(detectedLanguageCode)
                    .text(message)
                    .build();

            TranslateTextResponse textResponse = translateClient.translateText(translateTextRequest);
            return textResponse.translatedText();
        }catch (TranslateException exception){
            System.err.println(exception.getMessage());
            System.exit(1);
        }
        return "";
    }

    private String translateToEnglish(String detectedLanguageCode, String text) {
        Region region = Region.US_EAST_1;
        TranslateClient translateClient = TranslateClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(region)
                .build();
        try{
            TranslateTextRequest textRequest = TranslateTextRequest.builder()
                    .sourceLanguageCode(detectedLanguageCode)
                    .targetLanguageCode("en")
                    .text(text)
                    .build();

            TranslateTextResponse textResponse = translateClient.translateText(textRequest);
            return textResponse.translatedText();
        }catch (TranslateException exception){
            System.err.println(exception.getMessage());
            System.exit(1);
        }
        return "";
    }

    private String DetectLanguage(String text) {
        Region region = Region.US_EAST_1;
        ComprehendClient comprehendClient = ComprehendClient.builder()
                .region(region)
                .build();

        try{

            String languageCode = "";
            DetectDominantLanguageRequest request = DetectDominantLanguageRequest.builder()
                    .text(text)
                    .build();

            DetectDominantLanguageResponse response = comprehendClient.detectDominantLanguage(request);

            List<DominantLanguage> dominantLanguages = response.languages();

            Iterator<DominantLanguage> iterator = dominantLanguages.iterator();

            while (iterator.hasNext()){
                DominantLanguage dominantLanguage = iterator.next();
                languageCode = dominantLanguage.languageCode();
            }
            return languageCode;
        }catch (ComprehendException exception){
            System.err.println(exception.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }
}

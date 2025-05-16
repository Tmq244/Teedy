package com.sismics.docs.rest.resource;

import com.sismics.docs.core.constant.PermType;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.util.JsonUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Translation REST resources.
 * 
 * @author bgamard
 */
@Path("/translation")
public class TranslationResource extends BaseResource {
    /**
     * Translate text.
     *
     * @api {post} /translation Translate text
     * @apiName PostTranslation
     * @apiGroup Translation
     * @apiParam {String} text Text to translate
     * @apiParam {String} sourceLanguage Source language (en or zh)
     * @apiSuccess {String} translation Translated text
     * @apiError (client) ForbiddenError Access denied
     * @apiError (server) TranslationError Error translating text
     * @apiPermission user
     * @apiVersion 1.5.0
     *
     * @param input JSON input containing text and source language
     * @return Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response translate(JsonObject input) {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        String text = input.getString("text", null);
        String sourceLanguage = input.getString("sourceLanguage", null);
        
        if (text == null || text.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Json.createObjectBuilder().add("error", "Text is required").build())
                    .build();
        }
        
        if (sourceLanguage == null || (!sourceLanguage.equals("en") && !sourceLanguage.equals("zh"))) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Json.createObjectBuilder().add("error", "Source language must be 'en' or 'zh'").build())
                    .build();
        }
        
        String targetLanguage = sourceLanguage.equals("zh") ? "en" : "zh";
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://api.siliconflow.cn/v1/chat/completions");
            httpPost.setHeader("Authorization", "Bearer sk-hdqewqiglinwfhhiurzzccyknfntyrwigzbjvmcbcxccwtvg");
            httpPost.setHeader("Content-Type", "application/json");
            
            String requestBody = Json.createObjectBuilder()
                    .add("model", "Qwen/Qwen2.5-72B-Instruct")
                    .add("messages", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder()
                                    .add("role", "system")
                                    .add("content", "You are a professional translator. Translate the following text from " + 
                                            (sourceLanguage.equals("zh") ? "Chinese to English" : "English to Chinese") + 
                                            ". Only output the translated text, nothing else."))
                            .add(Json.createObjectBuilder()
                                    .add("role", "user")
                                    .add("content", text)))
                    .add("temperature", 0.7)
                    .add("max_tokens", 4000)
                    .build()
                    .toString();
            
            httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
            
            try (CloseableHttpResponse response = client.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    String translation = JsonUtil.extractString(responseBody, "choices[0].message.content");
                    
                    if (translation != null && !translation.isEmpty()) {
                        JsonObjectBuilder responseBuilder = Json.createObjectBuilder()
                                .add("translation", translation);
                        return Response.ok(responseBuilder.build()).build();
                    } else {
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(Json.createObjectBuilder().add("error", "Invalid translation response").build())
                                .build();
                    }
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(Json.createObjectBuilder().add("error", "Empty response from translation service").build())
                            .build();
                }
            }
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Json.createObjectBuilder().add("error", "Error calling translation service: " + e.getMessage()).build())
                    .build();
        }
    }
} 

// AI-Generation: by Cursor
// prompt： Translating a document to other languages after opening it.
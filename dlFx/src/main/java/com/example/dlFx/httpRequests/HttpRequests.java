package com.example.dlFx.httpRequests;

import com.example.dlFx.dto.AuthorizedUserDto;
import com.example.dlFx.dto.main.MainDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Класс для выполнения HTTP-запросов.
 */
public class HttpRequests<T> {

    public static final String AUTH_EXCEPTION = "Incorrect login or password";
    public static final String URI = "http://localhost:8080/";

    @Setter
    private static String TOKEN = "";

    /**
     * Метод для выполнения аутентификационного запроса.
     *
     * @param dto DTO с данными пользователя для аутентификации.
     * @param uri конечный путь для запроса.
     * @return ok или AUTH_EXCEPTION.
     * @throws IOException          если происходит ошибка ввода-вывода.
     * @throws InterruptedException если запрос прерван.
     * @throws URISyntaxException   если URI имеет неверный синтаксис.
     */
    public static String AuthRequest(AuthorizedUserDto dto, String uri) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI + uri))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                .build();
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        if (response.body().contains(AUTH_EXCEPTION)) {
            return AUTH_EXCEPTION;
        }
        setTOKEN(response.body());
        return "ok";
    }

    /**
     * Метод для выполнения POST-запроса.
     *
     * @param dto DTO с данными для отправки.
     * @param uri конечный путь для запроса.
     * @return JSON-ответ в виде JsonNode.
     * @throws IOException          если происходит ошибка ввода-вывода.
     * @throws InterruptedException если запрос прерван.
     * @throws URISyntaxException   если URI имеет неверный синтаксис.
     */
    public static <T extends MainDto> JsonNode PostRequest(T dto, String uri) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(redactUrl(URI + uri)))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer " + TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                .build();
        HttpResponse<InputStream> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofInputStream()
        );
        InputStream responseBody = response.body();
        return objectMapper.readTree(responseBody);
    }

    /**
     * Метод для выполнения GET-запроса.
     *
     * @param uri конечный путь для запроса.
     * @return JSON-ответ в виде JsonNode.
     * @throws IOException          если происходит ошибка ввода-вывода.
     * @throws InterruptedException если запрос прерван.
     * @throws URISyntaxException   если URI имеет неверный синтаксис.
     */
    public static JsonNode GetRequest(String uri) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(redactUrl(URI + uri)))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer " + TOKEN)
                .GET()
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<InputStream> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofInputStream()
        );
        InputStream responseBody = response.body();
        return objectMapper.readTree(responseBody);
    }

    /**
     * Метод для выполнения PUT-запроса.
     *
     * @param dto DTO с данными для отправки.
     * @param uri конечный путь для запроса.
     * @return JSON-ответ в виде JsonNode.
     * @throws IOException          если происходит ошибка ввода-вывода.
     * @throws InterruptedException если запрос прерван.
     * @throws URISyntaxException   если URI имеет неверный синтаксис.
     */
    public static <T extends MainDto> JsonNode PutRequest(T dto, String uri) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(redactUrl(URI + uri)))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer " + TOKEN)
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                .build();
        HttpResponse<InputStream> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofInputStream()
        );
        InputStream responseBody = response.body();
        return objectMapper.readTree(responseBody);
    }

    /**
     * Метод для выполнения DELETE-запроса.
     *
     * @param uri конечный путь для запроса.
     * @throws IOException          если происходит ошибка ввода-вывода.
     * @throws InterruptedException если запрос прерван.
     * @throws URISyntaxException   если URI имеет неверный синтаксис.
     */
    public static void DeleteRequest(String uri) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(redactUrl(URI + uri)))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer " + TOKEN)
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    }

    /**
     * Вспомогательный метод для замены пробелов на символы подчеркивания в URL.
     *
     * @param url строка URL.
     * @return отредактированная строка URL.
     */
    private static String redactUrl(String url) {
        return url.replaceAll(" ", "_");
    }
}

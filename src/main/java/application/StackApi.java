package application;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StackApi {

    String ENDPOINT = "https://api.stackexchange.com";
    Project project;

    public StackApi(Project project) {
        this.project = project;
    }

    public String getAnswers(String query) {

        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(ENDPOINT);
            uriBuilder.addParameter("order", "desc");
            uriBuilder.addParameter("sort", "votes");
            uriBuilder.addParameter("accepted", "True");
            uriBuilder.addParameter("site", "stackoverflow");
            uriBuilder.addParameter("q", query);
            uriBuilder.setPath("/2.2/search/advance");
        } catch (URISyntaxException e) {
            Notification notification = new Notification("", "Stack Development",
                    "Invalid URI for stackoverflow API call: " + ENDPOINT, NotificationType.ERROR);
            notification.notify(project);
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse.BodyHandler bodyHandler = new HttpResponse.BodyHandler() {
            @Override
            public HttpResponse.BodySubscriber apply(HttpResponse.ResponseInfo responseInfo) {
                Notification notification = new Notification("", "Stack Development",
                        "Flimflam: " + ENDPOINT, NotificationType.ERROR);
                notification.notify(project);
                return null;
            }
        };

        try {
            httpClient.send(httpRequest, bodyHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}

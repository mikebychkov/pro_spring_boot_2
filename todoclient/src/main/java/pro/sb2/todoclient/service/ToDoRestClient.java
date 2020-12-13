package pro.sb2.todoclient.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.sb2.todoclient.ex.ToDoErrorHandler;
import pro.sb2.todoclient.model.ToDo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ToDoRestClient {

    private RestTemplate rest;
    private ToDoRestClientProperties properties;

    public ToDoRestClient(ToDoRestClientProperties properties) {
        this.rest = new RestTemplate();
        this.rest.setErrorHandler(new ToDoErrorHandler());
        this.properties = properties;
    }

    public Iterable<ToDo> findAll() throws URISyntaxException {
        RequestEntity<Iterable<ToDo>> request = new RequestEntity<>(HttpMethod.GET
                , new URI(properties.getUrl() + properties.getBasePath()));
        ResponseEntity<Iterable<ToDo>> response = rest.exchange(request
                , new ParameterizedTypeReference<Iterable<ToDo>>() { });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public ToDo findById(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return rest.getForObject(properties.getUrl() + properties.getBasePath(), ToDo.class, params);
    }

    public ToDo upsert(ToDo toDo) throws URISyntaxException {
        RequestEntity<?> request = new RequestEntity<>(toDo, HttpMethod.POST
                , new URI(properties.getUrl() + properties.getBasePath()));
        ResponseEntity<?> response = rest.exchange(request
                , new ParameterizedTypeReference<ToDo>() { });
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return rest.getForObject(response.getHeaders().getLocation(), ToDo.class);
        }
        return null;
    }

    public ToDo setCompleted(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        rest.postForObject(properties.getUrl() + properties.getBasePath() + "/{id}?_method=patch"
                , null, ResponseEntity.class, params);
        return findById(id);
    }

    public void delete(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        rest.delete(properties.getUrl() + properties.getBasePath() + "/{id}", params);
    }
}

package demo.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import demo.persistence.models.Person;
import demo.persistence.models.User;
import demo.persistence.repositories.PersonRepository;
import demo.persistence.repositories.UserRepository;
import common.base.services.database.DataBaseManagerService;
import common.base.services.util.ResponseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DataBaseManagerService dataBaseManagerService;

    @Autowired
    ResponseManagerService responseManagerService;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody JsonNode entryData){
        String firstName = entryData.get("firstName").asText();
        String lastName = entryData.get("lastName").asText();
        String identification = entryData.get("identification").asText();
        String email = entryData.get("email").asText();
        Person person = new Person(firstName,lastName,identification,email);
        person.setId(Long.getLong("1"));
        personRepository.save(person);
        String userName = entryData.get("userName").asText();
        String password = entryData.get("password").asText();
        String profile = entryData.get("profile").asText();
        User user= new User(userName,password,profile,person);
        userRepository.save(user);
        return ResponseEntity.ok("Guardado");
    }

    @GetMapping("/getPerson")
    public ResponseEntity<Object> getPerson(@RequestParam MultiValueMap<String,String> params) throws Exception {
        Long personId = Long.valueOf(params.getFirst("personId"));
        List<Person> persons = personRepository.findAllByDelete(false, PageRequest.of(0,2));
        Person person = personRepository.findById(personId).get();
        String query = "SELECT p.id, p.first_name FROM admin.person p";
        Map map = new HashMap<>();
        map.put("status",false);
        map.put("code",400);
        map.put("data","Prueba de error");
        responseManagerService.simpleFunctionVerify.accept(map);
        Function<Object[],Object> format = result -> result[0];
        Function<Person,Map> formatPersons = (Person personItem) -> {
            return personItem.mapDescriptor();
        };
        System.out.println(dataBaseManagerService.executeNativeListQuery(query,format).get(0));
        List lista = persons.stream().map(formatPersons).collect(Collectors.toList());
        return responseManagerService.listResponse(lista);
    }

}

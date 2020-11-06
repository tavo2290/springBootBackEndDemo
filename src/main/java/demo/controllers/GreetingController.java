package demo.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import demo.persistence.models.Greeting;
import common.base.util.repositories.validation.FormatAndValidationRepository;
import common.base.util.repositories.validation.FieldVal;
import common.base.util.repositories.validation.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private static final String template = "HELLO, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    FormatAndValidationRepository formatAndValidationRepository;

    @GetMapping()
    public Greeting greeting(@RequestParam(value = "name",defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template,name));
    }

    @GetMapping("/multiParam")
    public ResponseEntity<Object> multiParam(@RequestParam MultiValueMap<String,String> params){
        System.out.println(params);
        return ResponseEntity.ok("Todo Bien");
    }

    @PostMapping()
    public ResponseEntity<Object> postTest(@RequestBody Student student){
        System.out.println(student.getId());
        return ResponseEntity.ok(student);
    }

    @PostMapping("/testCompletJson")
    public ResponseEntity<Object> completPostTest(@RequestBody JsonNode entryData) throws Exception{
        List<FieldVal> list = new ArrayList<>();
        list.add(new FieldVal("name",true,"String"));
        list.add(new FieldVal("lastName",true,"String"));
        list.add(new FieldVal("id",false,"Long"));
        Map<String,Object> castingMap = formatAndValidationRepository.validateEntryData(list,entryData);
        System.out.println(castingMap);
        return ResponseEntity.ok("Todo Bien");
    }

    @PostMapping("/testJsonToObject")
    public ResponseEntity<Object> mapToObject(@RequestBody JsonNode entryData) throws Exception{
        List<FieldVal> list = new ArrayList<>();
        list.add(new FieldVal("id",true,"Long"));
        list.add(new FieldVal("content",true,"String"));
        Map castingMap = formatAndValidationRepository.validateEntryData(list,entryData);
        Greeting g = new Greeting();
        formatAndValidationRepository.populateObjectFromMap(castingMap,g);
        System.out.println(g.getId());
        return ResponseEntity.ok("Todo Bien");
    }

    @GetMapping("/testException")
    public ResponseEntity<Object> getPerson(@RequestParam MultiValueMap<String,String> params) throws Exception {
        throw new Exception("Una Excepcion");
    }
}

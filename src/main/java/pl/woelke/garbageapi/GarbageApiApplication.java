package pl.woelke.garbageapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.woelke.garbageapi.utility.SheduleDataGenerator;

import java.io.IOException;
import java.util.HashMap;

@SpringBootApplication
public class GarbageApiApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GarbageApiApplication.class, args);


        SheduleDataGenerator sheduleDataGenerator = new SheduleDataGenerator();
        HashMap<String[], String[]> sheduleGeneratedData = sheduleDataGenerator.generateSheduleData();
//        sheduleDataGenerator.printSheduleData(sheduleGeneratedData);
        sheduleDataGenerator.createSheduleDatabase(sheduleGeneratedData);
    }
}

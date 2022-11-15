package pl.woelke.garbageapi.utility;

import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class SheduleDataGenerator {

    private static final Logger LOGGER = Logger.getLogger(SheduleDataGenerator.class.getName());

    public SheduleDataGenerator() {
    }

    public HashMap<String[], String[]> generateSheduleData() throws IOException {

        LOGGER.info("generateSheduleData()");
        URL url = new URL("http://www.zkgpch.pl/images/stories/Chemno.pdf");

        CopyFileFromUrlSource urlFile = new CopyFileFromUrlSource(url);
        File savedFromUrlFile = urlFile.readDataFile();
        MapperPDFtoHashMap MapperPDFtoHashMap = new MapperPDFtoHashMap();
        HashMap<String[], String[]> shedule = MapperPDFtoHashMap.getShedule(savedFromUrlFile, 130, 350, 800, 20);
        return shedule;

    }

    public void printSheduleData(HashMap<String[], String[]> shedule) {

        LOGGER.info("printSheduleData()");
        for (Map.Entry<String[], String[]> entry : shedule.entrySet()) {
            String[] months = entry.getKey();
            String[] pickupDays = entry.getValue();

            for (int i = 0; i < months.length - 1; i++) {
                System.out.print(months[i] + " " + pickupDays[i] + "\n");
            }
        }
    }

    public void createSheduleDatabase(HashMap<String[], String[]> shedule) {
        LOGGER.info("createSheduleDatabase()");
        StringBuilder sheduleBuilder = new StringBuilder();

        for (Map.Entry<String[], String[]> entry : shedule.entrySet()) {
            String[] months = entry.getKey();
            String[] pickupDays = entry.getValue();

            int id = 1;
            for (int i = 0; i < months.length - 1; i++) {
                sheduleBuilder.append("insert into shedule (id, month, days)" +
                        "values (" + (id + i) + ",'" + months[i] + "','" + pickupDays[i] + "');\n");
            }
            saveData(sheduleBuilder);
        }
    }

    public void saveData(StringBuilder stringBuilder) {

        LOGGER.info("saveData()");
        File dataSql = new File("src/main/resources/data.sql");

        try {
            dataSql.createNewFile();
            Writer targedDataSql = new FileWriter(dataSql);
            targedDataSql.write(stringBuilder.toString());
            targedDataSql.flush();
            targedDataSql.close();
            LOGGER.info("dataSql file saved and closed" );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

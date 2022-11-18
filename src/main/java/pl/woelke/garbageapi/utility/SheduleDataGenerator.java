package pl.woelke.garbageapi.utility;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class SheduleDataGenerator {

    private static final Logger LOGGER = Logger.getLogger(SheduleDataGenerator.class.getName());
    private HashMap<String[], String[]> shedule = new HashMap<>();

    private File file;
    private int setXpos = 130;
    private int setYpos = 350;
    private int width = 800;
    private int high = 20;

    public SheduleDataGenerator() {
    }

    public SheduleDataGenerator(File file, int setXpos, int setYpos, int width, int high) {
        this.file = file;
        this.setXpos = setXpos;
        this.setYpos = setYpos;
        this.width = width;
        this.high = high;
    }

    public void generateSheduleData() throws IOException {

        LOGGER.info("generateSheduleData()");
        URL url = new URL("http://www.zkgpch.pl/images/stories/Chemno.pdf");

        File file = readDataFile(url);
        PdfParser pdfParser = new PdfParser();
        String kDaysTextByArea = pdfParser.ExtractTextByArea(file, setXpos, setYpos, width, high);
        String[] days = kDaysTextByArea.split(" ");
        String[] months = new String[days.length];

        for (int i = 0; i < days.length - 1; i++) {
            months[i] = String.valueOf(i);
//      LOGGER.info("month: " + (month + i) + "  days: " + days[i]);
            shedule.put(months, days);
        }
        createSheduleDatabase(shedule);
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


    public File readDataFile(URL url) throws IOException {
        LOGGER.info("readUrlFile(" + url + ")");

        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[131072];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream("src/main/resources/Chelmno.pdf");
        fos.write(response);
        fos.close();
        File file = new File("src/main/resources/Chelmno.pdf");
        return file;

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
            LOGGER.info("dataSql file saved and closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

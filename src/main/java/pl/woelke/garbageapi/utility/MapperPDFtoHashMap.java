package pl.woelke.garbageapi.utility;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

public class MapperPDFtoHashMap {

    private static final Logger LOGGER = Logger.getLogger(MapperPDFtoHashMap.class.getName());
    private HashMap<String[], String[]> shedule = new HashMap<>();

    public HashMap<String[], String[]> getShedule(File fileName, int setX, int setY, int widht, int high) {
        LOGGER.info("getShedule()");

        PdfParser pdfParser = new PdfParser();
        String kDaysTextByArea = pdfParser.ExtractTextByArea(fileName, setX, setY, widht, high);
        String[] days = kDaysTextByArea.split(" ");
        String[] months = new String[days.length];

        for (int i = 0; i < days.length - 1; i++) {
            months[i] = String.valueOf(i);
//      LOGGER.info("month: " + (month + i) + "  days: " + days[i]);
            shedule.put(months, days);
        }
        LOGGER.info("getShedule(...)");
        return shedule;
    }
}

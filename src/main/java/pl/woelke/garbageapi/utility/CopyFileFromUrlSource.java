package pl.woelke.garbageapi.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public class CopyFileFromUrlSource {

    private static final Logger LOGGER = Logger.getLogger(CopyFileFromUrlSource.class.getName());
        private URL url;

    public CopyFileFromUrlSource(URL url) {
        this.url = url;
    }

    public File readDataFile() throws IOException {
        LOGGER.info("readUrlFile(" + url+ ")");

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
}

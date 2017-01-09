package br.ifpb.simba.ourdata.evaluation.process;

import br.ifpb.simba.ourdata.dao.entity.KeyTimeBdDao;
import br.ifpb.simba.ourdata.entity.KeyTime;
import br.ifpb.simba.ourdata.entity.utils.KeyTimeUtils;
import br.ifpb.simba.ourdata.reader.KeyTimeBo;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class TimeProcess {

    public boolean process(String local, int colum1, int colum2, String resourceId) {

        KeyTimeBo keyTimeBo = new KeyTimeBo(KeyTimeBo.NUM_ROWS_CHECK_DEFAULT);
        KeyTimeBdDao keyPlaceBdDao = new KeyTimeBdDao();

        List<KeyTime> keyTimes = new ArrayList<>();

        InputStream in = null;
        try {
            in = new FileInputStream(new File(local));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlaceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            keyTimes.addAll(keyTimeBo.getKeyTimes(in, colum1, colum2, resourceId));
            keyTimes = KeyTimeUtils.getLiteVersion(keyTimes);

            if (!keyTimes.isEmpty()) {
                System.out.println("Inserindo keyTimes ...");
            } else {
                System.out.println("Nenhuma keyTimes foi encontrada!");
            }

            if (!keyTimes.isEmpty()) {
                keyPlaceBdDao.insertAllRR(keyTimes);
            }

        } catch (OutOfMemoryError | IOException | JDOMException | DocumentCreationTimeMissingException ex) {
//                    System.out.println(TextColor.ANSI_RED.getCode() + " " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.avaliation.process;

import br.ifpb.simba.ourdata.avaliation.database.PlaceAvaliationDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.utils.KeyPlaceUtils;
import br.ifpb.simba.ourdata.reader.CSVReaderOD;
import br.ifpb.simba.ourdata.reader.KeyPlaceBo;
import br.ifpb.simba.ourdata.reader.TextColor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wensttay
 */
public class PlaceProcess {

    public boolean process(String local, int colum, String type, String resourceId, boolean isFile){

        KeyPlaceBo keyPlacesBo = new KeyPlaceBo(KeyPlaceBo.NUM_ROWS_CHECK_DEFAULT);

        PlaceAvaliationDao keyPlaceBdDao = new PlaceAvaliationDao();
        List<KeyPlace> keyPlaces = new ArrayList<>();

        CSVReaderOD cSVReaderOD = new CSVReaderOD();
        InputStream in = null;
        
        if (isFile) {
            System.out.println("Processando CSV via arquivo");
            try {
                in = new FileInputStream(new File(local));
            } catch (FileNotFoundException ex) {
                System.out.println("ERROR: Erro ao tentar abrir o arquivo CSV");
                return false;
            }
        } else {
            try {
                System.out.println("Processando CSV via URL ...");
                URL stackURL = new URL(local);
                stackURL.openConnection().setReadTimeout(120000);
                in = stackURL.openStream();
            } catch (IOException ex) {
                in  = null;
                System.out.println("ERROR: Erro ao tentar se conectar à URL");
                return false;
            }
        }

        try {
            if (in != null) {
                keyPlaces.addAll(keyPlacesBo.getKeyPlaces(in, colum, type, resourceId));
                keyPlaces = KeyPlaceUtils.getLiteVersion(keyPlaces);

                if (!keyPlaces.isEmpty()) {
                    System.out.println("Inserindo keyPlaces ...");
                } else {
                    System.out.println("Nenhuma keyPlace foi encontrada!");
                }

                if (!keyPlaces.isEmpty()) {
                    keyPlaceBdDao.insertAll(keyPlaces);
                    System.out.println("Inseridos Com Succeso!");
                }
            }

        } catch (OutOfMemoryError | IOException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + " " + ex.getMessage());
            return false;
        }
        
        return true;
    }
}

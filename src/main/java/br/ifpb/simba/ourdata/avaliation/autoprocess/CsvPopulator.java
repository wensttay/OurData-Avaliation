/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.avaliation.autoprocess;

import br.ifpb.simba.ourdata.avaliation.process.PlaceProcess;
import br.ifpb.simba.ourdata.avaliation.process.TimeProcess;
import br.ifpb.simba.ourdata.reader.CSVReaderOD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CsvPopulator {

    PlaceProcess placeProcess;
    TimeProcess timeProcess;
    CSVReaderOD cSVReaderOD;

    public CsvPopulator() {
        placeProcess = new PlaceProcess();
        timeProcess = new TimeProcess();
        cSVReaderOD = new CSVReaderOD();
    }

    public void process(String url) throws FileNotFoundException, IOException {
        List<String> errorList = new ArrayList<>();

        InputStream in = new FileInputStream(new File(url));
        List<String[]> build = cSVReaderOD.build(in);

        int rowSize = build.size();
        for (int row = 1064; row < rowSize; row++) {

            String[] currentColumn = build.get(row);
            int columnSize = currentColumn.length;

            String resourceId = null;
            String resourceUrl = null;
            Integer geoColumn = null;
            String geoType = null;
            Integer timeStartColumn = null;
            Integer timeFinalColumn = null;

            for (int column = 0; column < columnSize; column++) {
                String value = currentColumn[column].trim();

                if (value != null && !value.isEmpty()) {
                    switch (column) {
                        case 0:
                            resourceId = value;
                            break;
                        case 1:
                            resourceUrl = value;
                            break;
                        case 2:
                            geoColumn = Integer.valueOf(value);
                            break;
                        case 3:
                            geoType = value;
                            break;
                        case 4:
                            timeStartColumn = Integer.valueOf(value);
                            break;
                        case 5:
                            timeFinalColumn = Integer.valueOf(value);
                            break;
                    }
                }
            }
            System.out.println("\nRow Number: " + row);
            System.out.println("Dados do Resource:\nID: " + resourceId + "\nURL: " + resourceUrl);
            if (geoColumn != null) {
                if (!placeProcess.process(resourceUrl, geoColumn, geoType, resourceId, false)) {
                    errorList.add(resourceId);
                }
                System.out.println("Processamento conluido com Sucesso!!\n");
            } else {
                System.out.println("Resource Não Possui Coluna Geografica\n");
            }

//            if (timeStartColumn != null) {
//                if (timeFinalColumn == null) {
//                    timeFinalColumn = timeStartColumn;
//                }
//                if (!timeProcess.process(resourceUrl, timeStartColumn, timeFinalColumn, resourceId)) {
//                    errorList.add(resourceUrl);
//                }
//            }

            System.out.println("RESOURCES WITH PROBLEM: ");
            for (String rId : errorList) {
                System.out.println(rId);
            }

        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        CsvPopulator cp = new CsvPopulator();
        cp.process("/home/wensttay/Downloads/Resource Places Details - Main.csv");
    }
}

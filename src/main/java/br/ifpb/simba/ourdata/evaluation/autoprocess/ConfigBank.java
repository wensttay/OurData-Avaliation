package br.ifpb.simba.ourdata.evaluation.autoprocess;

import br.ifpb.simba.ourdata.evaluation.database.PlaceAvaliationDao;
import br.ifpb.simba.ourdata.dao.entity.KeyPlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import java.util.List;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class ConfigBank {

    public static void main(String[] args) {
        KeyPlaceBdDao bdDao = new KeyPlaceBdDao();
        List<KeyPlace> allOnAvaliation = bdDao.getAllOnAvaliation();
        PlaceAvaliationDao avaliationDao = new PlaceAvaliationDao();

        avaliationDao.setProperties_path("/banco/bancoEvaluation.properties");
        boolean insertAll = avaliationDao.insertAll(allOnAvaliation);
        System.out.println("Deu Certo? " + insertAll);
    }
}

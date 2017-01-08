/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.avaliation.autoprocess;

import br.ifpb.simba.ourdata.avaliation.database.PlaceAvaliationDao;
import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.dao.entity.KeyPlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class ConfigBank {
    public static void main(String[] args) {
        KeyPlaceBdDao bdDao = new KeyPlaceBdDao();
        List<KeyPlace> allOnAvaliation = bdDao.getAllOnAvaliation();
        PlaceAvaliationDao avaliationDao = new PlaceAvaliationDao();
        
        avaliationDao.setProperties_path("/banco/bancoAvaliation.properties");
        boolean insertAll = avaliationDao.insertAll(allOnAvaliation);
        System.out.println("Deu Certo? " + insertAll);
    }
}

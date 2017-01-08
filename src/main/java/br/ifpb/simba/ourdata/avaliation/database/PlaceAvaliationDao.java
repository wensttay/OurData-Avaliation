/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.avaliation.database;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.reader.TextColor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class PlaceAvaliationDao extends GenericBdDao {

    public boolean insertAll(List<KeyPlace> listKeyPlaces) {
        try {
            conectar();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            return false;
        }
        int count = 1;
        for (KeyPlace keyPlace : listKeyPlaces) {
            insert(keyPlace);
            System.out.println("Inseriu o Número: " + count++);
        }

        try {
            desconectar();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            return false;
        }

        return true;
    }

    private boolean insert(KeyPlace keyPlace) {
        PreparedStatement ps = null;
        boolean result;
        
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO resource_place_avaliation(COLUM_NUMBER, COLUM_VALUE,");
            sql.append("REPEAT_NUMBER, ROWS_NUMBER, METADATA_CREATED, WAY, minX, minY, maxX, maxY, ID_PLACE, ID_RESOURCE)");
            sql.append("VALUES(?, ?, ?, ?, ?, ST_GeomFromText(?), ?, ?, ?, ?, ?, ?)");
            ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setInt(i++, keyPlace.getColumNumber());
            ps.setString(i++, keyPlace.getColumValue());
            ps.setInt(i++, keyPlace.getRepeatNumber());
            ps.setInt(i++, keyPlace.getRowsNumber());
            ps.setTimestamp(i++, keyPlace.getMetadataCreated());
            ps.setObject(i++, keyPlace.getPlace().getWay().toString());
            ps.setDouble(i++, keyPlace.getPlace().getMinX());
            ps.setDouble(i++, keyPlace.getPlace().getMinY());
            ps.setDouble(i++, keyPlace.getPlace().getMaxX());
            ps.setDouble(i++, keyPlace.getPlace().getMaxY());

            ps.setInt(i++, keyPlace.getPlace().getId());
            ps.setString(i++, keyPlace.getIdResource());

            result = (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        try {
            ps.close();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }

        return result;
    }

}

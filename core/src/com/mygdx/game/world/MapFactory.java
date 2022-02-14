package com.mygdx.game.world;

import com.mygdx.game.world.maps.Antikva;
import com.mygdx.game.world.maps.Castle;
import com.mygdx.game.world.maps.DistrictTimulo;
import com.mygdx.game.world.maps.Leprozia;
import com.mygdx.game.world.maps.Mehan;
import com.mygdx.game.world.maps.RoofKBMOffice;
import com.mygdx.game.world.maps.SpaceStation;
import com.mygdx.game.world.maps.TestMap;
import com.mygdx.game.world.maps.ViClub;

import java.util.Hashtable;

public class MapFactory {

    public static enum MapType{
        TESP_MAP,
        SPACE_STATION,
        CASTLE,
        MEHAN,
        ANTIKVA,
        LEPROZIA,
        TIMULO,
        VI_CLUB,
        ROOF_KBM_OFFICE

    }

    private static Hashtable<MapType,Map> mapTable = new Hashtable<MapType, Map>();


    static public Map getMap(MapType mapType, MapManager mapManager) {
        Map map = null;
        switch(mapType) {
            case TESP_MAP:
                map = mapTable.get(MapType.TESP_MAP);
                if( map == null ){
                    map = new TestMap();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.TESP_MAP, map);
                }
                break;
            case SPACE_STATION:
                map = mapTable.get(MapType.SPACE_STATION);
                if( map == null ){
                    map = new SpaceStation();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.SPACE_STATION, map);
                }
                break;
            case CASTLE:
                map = mapTable.get(MapType.CASTLE);
                if( map == null ){
                    map = new Castle();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.CASTLE, map);
                }
                break;
            case MEHAN:
                map = mapTable.get(MapType.MEHAN);
                if( map == null ){
                    map = new Mehan();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.MEHAN, map);
                }
                break;
            case ANTIKVA:
                map = mapTable.get(MapType.ANTIKVA);
                if( map == null ){
                    map = new Antikva();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.ANTIKVA, map);
                }
                break;
            case LEPROZIA:
                map = mapTable.get(MapType.LEPROZIA);
                if( map == null ){
                    map = new Leprozia();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.LEPROZIA, map);
                }
                break;
            case TIMULO:
                map = mapTable.get(MapType.TIMULO);
                if( map == null ){
                    map = new DistrictTimulo();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.TIMULO, map);
                }
                break;
            case VI_CLUB:
                map = mapTable.get(MapType.VI_CLUB);
                if( map == null ){
                    map = new ViClub();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.VI_CLUB, map);
                }
                break;
            case ROOF_KBM_OFFICE:
                map = mapTable.get(MapType.ROOF_KBM_OFFICE);
                if( map == null ){
                    map = new RoofKBMOffice();
                    map.addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));
                    mapTable.put(MapType.ROOF_KBM_OFFICE, map);
                }
                break;
        }
        return map;
    }

    public static void clearCache(){
        for( Map map: mapTable.values()){
            map.dispose();
        }
        mapTable.clear();
    }
}

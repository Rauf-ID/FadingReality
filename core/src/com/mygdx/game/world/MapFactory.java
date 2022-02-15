package com.mygdx.game.world;

import java.util.Hashtable;

public class MapFactory {

    public enum MapType{
        TEST_MAP("textures/maps/earth.tmx"),
        SPACE_STATION("textures/maps/spaceStation/SpaceStation.tmx"),
        CASTLE("textures/maps/antikva/castle/Castle.tmx"),
        MEHAN("textures/maps/mehan/Mehan.tmx"),
        ANTIKVA("textures/maps/antikva/Antikva.tmx"),
        LEPROZIA("textures/maps/leprozia/Leprozia.tmx"),
        TIMULO("textures/maps/earth/districtTimulo/TimuloTest.tmx"),
        VI_CLUB("textures/maps/earth/viClub/ViClub.tmx"),
        ROOF_KBM_OFFICE("textures/maps/earth/roofKBMOffice/RoofKBMOffice.tmx");

        private String path;

        MapType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private static Hashtable<MapType, Map> mapTable = new Hashtable<>();


    static public Map getMap(MapType mapType, MapManager mapManager) {
        Map map = mapTable.get(mapType);
        if( map == null ){
            map = new Map(mapManager, mapType);
            mapTable.put(mapType, map);
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

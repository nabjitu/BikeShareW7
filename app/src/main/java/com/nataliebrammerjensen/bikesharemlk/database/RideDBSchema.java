package com.nataliebrammerjensen.bikesharemlk.database;

/**
 * Created by nataliebrammerjensen on 14/03/2018.
 */

public class RideDBSchema {
    public static final class RideTable {
        public static final String NAME = "rides";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String BIKENAME = "bikename";
            public static final String START = "start";
            public static final String STOP = "stop";
            public static final String DATE = "ddmmyy";
            public static final String TIME = "hhmmss";

        }
    }
}

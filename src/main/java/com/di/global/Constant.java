package com.di.global;

/**
 * Created by bentengdi on 2018/3/2.
 */
public class Constant {

        public static final String UNDER_LINE = "_";

        public static final String MIDDLE_LINE = "-";

        public static final String SEMICOLON  = ";";

        public static final String EQUAL  = "=";

        public static final String COLON  = ":";

        public static final String COMMA = ",";

        public static final String INVISIBLE_CHAR = "\000";

        public static class ActionType{

            public static final Integer NULLVALUE = 0;

            public static final Integer FLUSH = 1;

            public static final Integer LOAD = 2;
        }

        public static class Status{

            public static final Integer ONLINE = 1;

            public static final Integer OFFLINE = 2;

            public static final Integer UNUSED = 3;
        }

        public static class Operation{

            public static final String ADD = "add";

            public static final String UPDATE = "update";

            public static final String DELETE = "del";
        }

        public static class DataType{

            public static final String INFO = "info";

            public static final String CHANNEL = "channel";

            public static final String BASECATE = "cate";
        }

}

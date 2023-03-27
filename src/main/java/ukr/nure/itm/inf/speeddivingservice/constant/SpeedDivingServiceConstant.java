package ukr.nure.itm.inf.speeddivingservice.constant;

public class SpeedDivingServiceConstant {
    public static final String HOME_PAGE = "index";
    public static final String CHART_PAGE = "chart";

    private SpeedDivingServiceConstant() {
        //empty to avoid instantiating this constant class
    }

    public class InputFileColumnHeaderConstant {
        public static final String TYPE = "Вид соревнования";
        public static final String NAME = "Имя";
        public static final String SURNAME = "Фамилия";
        public static final String DATE = "Дата соревнования";
        public static final String RESULT = "Результат";

        private InputFileColumnHeaderConstant() {
            //empty to avoid instantiating this constant class
        }
    }
}

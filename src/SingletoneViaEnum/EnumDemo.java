package SingletoneViaEnum;


public class EnumDemo {
    public static void main(String[] args) {
        SingletoneEnum singletoneEnum = SingletoneEnum.INSTANCE;
        singletoneEnum.setValue(5);
        SingletoneEnum singletoneEnum2 = SingletoneEnum.INSTANCE;
        System.out.println(singletoneEnum2.getValue() == singletoneEnum.getValue());
    }
}

import java.io.IOException;

public class Today {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(IdentityStateEnum.getState(0));
        System.out.println(IdentityStateEnum.getState(1));
        System.out.println(IdentityStateEnum.getState(1));
        System.out.println(IdentityStateEnum.getState(1));
    }

}

enum IdentityStateEnum {
    success("认证失败", 0), fail("认证成功", 1);

    private String name;
    private int index;

    private IdentityStateEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getState(int index) {
        for (IdentityStateEnum c : IdentityStateEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
